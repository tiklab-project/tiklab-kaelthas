package io.thoughtware.kaelthas.common.websocket;

import com.alibaba.fastjson.JSONArray;
import io.thoughtware.rpc.common.context.RpcTenantHolder;
import io.thoughtware.kaelthas.db.dbTrigger.service.DbTriggerService;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.kaelthas.host.host.service.HostService;
import io.thoughtware.kaelthas.host.trigger.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ServerSocketConnect {


    @Autowired
    HistoryService historyService;

    @Autowired
    TriggerService triggerService;

    @Autowired
    HostService hostService;

    @Autowired
    private DbTriggerService dbTriggerService;

    private static final List<History> historyList = new ArrayList<>();

    private static final int BATCH_SIZE = 100;

    public final ExecutorService executorService = Executors.newCachedThreadPool();

    public final ExecutorService service = Executors.newCachedThreadPool();

    public final ExecutorService saveDataService = Executors.newCachedThreadPool();

    @Bean
    public void readSocketMessage() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            executorService.submit(() -> {
                while (true) {
                    //System.out.println("服务端监听开始。。。");
                    Socket clientConnectSocket = serverSocket.accept();
                    service.execute(() -> {
                        try {
                            System.out.println("监听到客户端连接。。。创建处理客户端连接的handler工具。。");
                            InputStream inputStream = clientConnectSocket.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            System.out.println("客户端已连接！");
                            System.out.println("socket:" + clientConnectSocket);
                            //不断读取客户端数据
                            String read;
                            while ((read = bufferedReader.readLine()) != null) {
                                //System.err.println("服务端收到的信息:" + read);
                                List<History> entityList = JSONArray.parseArray(read, History.class);
                                //System.err.println(entityList);
                                if (entityList == null || entityList.isEmpty()) {
                                    return;
                                }

                                historyList.addAll(entityList);

                                RpcTenantHolder.set(entityList.get(0).getTenant());

                                if (historyList.size() > BATCH_SIZE) {
                                    List<History> histories = new ArrayList<>(historyList);
                                    historyList.clear();

                                    saveDataService.submit(() -> {
                                        historyService.insertForList(histories);
                                    });

                                    //查看数据是否经过触发器生成告警数据
                                    saveDataService.submit(() -> {
                                        triggerService.insertAlarmForTrigger(histories);
                                    });

                                    /*saveDataService.submit(() ->{
                                        dbTriggerService.insertAlarmForDbTrigger(histories);
                                    });*/

                                }
                                //saveDataService.shutdown();
                            }
                            clientConnectSocket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}