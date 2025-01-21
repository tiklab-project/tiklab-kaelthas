package io.tiklab.kaelthas.internet.overview.service;

import io.tiklab.kaelthas.internet.history.service.InternetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 网络监控概况页
 */
@Service
public class InOverviewServiceImpl implements InOverviewService{


    @Autowired
    private InternetHistoryService internetHistoryService;

    /**
     * 获取网络详情
     */
    @Override
    public Map<String, Object> findInternetOverview(String internetId) {
        return internetHistoryService.findInternetOverview(internetId);
    }
}
