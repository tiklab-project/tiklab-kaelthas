package io.tiklab.kaelthas;

import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.history.service.HistoryServiceImpl;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.host.service.HostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = {HostServiceImpl.class, HistoryServiceImpl.class})
@RunWith(SpringRunner.class)
public class KaelthasApplicationTest {

    @Autowired
    private HostService hostService;

    @Autowired
    private HistoryService historyService;

    @Test
    void hostTimer(){
        List<Host> allHost = hostService.findAllHost();
        if (allHost.isEmpty()) {
            return;
        }

        List<String> hostIds = allHost.stream().map(Host::getId).toList();
        //通过主机的id查询出主机是否存在数据,存在数据则修改状态为1,如果没有数据,修改状态为0
        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);
        List<History> histories = historyService.findHistoryByHostIds(beforeTime);
        String updateSql;

        //如果查询的数据为空
        if (histories.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hostIds.size(); i++) {
                stringBuilder.append("'").append(hostIds.get(i).concat("'"));
                if (i != hostIds.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            updateSql = "update mtc_host set usability = 0 where id in ("+stringBuilder+");";
        }else {
            //主机分为两类,有数据的和没有数据的都需要进行状态更新
            List<String> resultIds = histories.stream().map(History::getId).toList();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < resultIds.size(); i++) {
                stringBuilder1.append("'").append(resultIds.get(i).concat("'"));
                if (i != resultIds.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
            updateSql = "update mtc_host set usability = 1 where id in ("+stringBuilder1+");";
            hostIds.removeAll(resultIds);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hostIds.size(); i++) {
                stringBuilder.append("'").append(hostIds.get(i).concat("'"));
                if (i != hostIds.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            updateSql = updateSql.concat("\nupdate mtc_host set usability = 0 where id in ("+stringBuilder+");");
        }

        //timerDao.updateBySQL(updateSql);
    }
}
