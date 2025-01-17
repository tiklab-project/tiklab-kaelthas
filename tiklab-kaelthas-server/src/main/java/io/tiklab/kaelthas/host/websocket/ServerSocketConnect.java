package io.tiklab.kaelthas.host.websocket;

import com.alibaba.fastjson.JSONArray;
import io.tiklab.core.exception.SystemException;
import io.tiklab.kaelthas.host.history.model.HostHistory;
import io.tiklab.kaelthas.host.history.service.HostHistoryService;
import io.tiklab.rpc.common.context.RpcTenantHolder;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.trigger.service.TriggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主机agent上报数据的接收
 */
@Service
public class ServerSocketConnect {
    private static Logger logger = LoggerFactory.getLogger(ServerSocketConnect.class);

    @Autowired
    HostHistoryService hostHistoryService;

    @Autowired
    TriggerService triggerService;

    @Autowired
    HostService hostService;

    private static final List<HostHistory> historyList = new ArrayList<>();

    //定义一个常量,如果达到这个阈值的话插入数据
    private static final int BATCH_SIZE = 1000;


    private  final Map<String,Long> timeMap =new HashMap();
    public final ExecutorService executorService = Executors.newCachedThreadPool();

    public final ExecutorService service = Executors.newCachedThreadPool();


    //创建一个线程,持续接收数据
    @Bean
    public void readSocketMessage() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            timeMap.put("time",System.currentTimeMillis());
            executorService.submit(() -> {

                while (true) {
                    logger.info("开始执行agent监听，端口：9999");
                    Socket clientConnectSocket = serverSocket.accept();
                    service.execute(() -> {
                        try {
                            InputStream inputStream = clientConnectSocket.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            logger.info("客户端已连接，socket："+clientConnectSocket);

                            //不断读取客户端数据
                            String read;
                            while ((read = bufferedReader.readLine()) != null) {

                                List<HostHistory> entityList = JSONArray.parseArray(read, HostHistory.class);

                                if (entityList == null || entityList.isEmpty()) {
                                    return;
                                }

                                historyList.addAll(entityList);

                                RpcTenantHolder.set(entityList.get(0).getTenant());

                                long endTime = System.currentTimeMillis();
                                long time = endTime-timeMap.get("time")  ;

                                //当采集的数量大于1000条或者时间3分 添加数据库一次
                                if (historyList.size() > BATCH_SIZE||time>=60000) {

                                    List<HostHistory> histories = new ArrayList<>(historyList);
                                    historyList.clear();

                                    hostHistoryService.insertForList(histories);
                                    timeMap.put("time",System.currentTimeMillis());
                                }
                            }
                            clientConnectSocket.close();
                        } catch (Exception e) {
                            throw new SystemException(e);
                        }
                    });
                }
            });
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }
}