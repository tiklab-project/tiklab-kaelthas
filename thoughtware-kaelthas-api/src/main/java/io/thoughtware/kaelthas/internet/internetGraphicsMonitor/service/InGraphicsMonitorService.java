package io.thoughtware.kaelthas.internet.internetGraphicsMonitor.service;

import java.util.List;

public interface InGraphicsMonitorService {

    void createGraphicsMonitorList(String string, List<String> monitorIds);

    void deleteByGraphics(String graphicsId);

    List<String> findMonitorIds(String id);

    void deleteByGraphicsIds(List<String> stringList);
}
