package io.tiklab.kaelthas.common.websocket;

import com.alibaba.fastjson.JSONArray;
import io.tiklab.rpc.common.context.RpcTenantHolder;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.trigger.service.TriggerService;
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

/**
 * 主机agent上报数据的接收
 */
@Service
public class ServerSocketConnect {


    @Autowired
    HistoryService historyService;

    @Autowired
    TriggerService triggerService;

    @Autowired
    HostService hostService;

    private static final List<History> historyList = new ArrayList<>();

    //定义一个常量,如果达到这个阈值的话插入数据
    private static final int BATCH_SIZE = 1000;

    public final ExecutorService executorService = Executors.newCachedThreadPool();

    public final ExecutorService service = Executors.newCachedThreadPool();


    //创建一个线程,持续接收数据
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

                                List<History> entityList = JSONArray.parseArray(read, History.class);

                                if (entityList == null || entityList.isEmpty()) {
                                    return;
                                }

                                historyList.addAll(entityList);

                                RpcTenantHolder.set(entityList.get(0).getTenant());

                                if (historyList.size() > BATCH_SIZE) {
                                    List<History> histories = new ArrayList<>(historyList);
                                    historyList.clear();

                                    historyService.insertForList(histories);

                                }
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