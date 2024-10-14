package io.tiklab.kaelthas.internet.internetMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internetMonitor.model.InternetMonitor;

import java.util.List;

public interface InternetMonitorService {

    Pagination<InternetMonitor> findMonitorPage(InternetMonitor internetMonitor);

    String createMonitor(InternetMonitor internetMonitor);

    void deleteMonitor(String id);

    void updateMonitor(InternetMonitor internetMonitor);

    List<InternetMonitor> findMonitorByInId(InternetMonitor internetMonitor);

    void deleteByInternet(String id);
}
