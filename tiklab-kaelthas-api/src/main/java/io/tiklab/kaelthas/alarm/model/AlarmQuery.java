package io.tiklab.kaelthas.alarm.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class AlarmQuery {

    private List<Order> orderParams = OrderBuilders.instance().desc("alertTime").get();

    private Page pageParam = new Page();

    // 监控主机id、监控数据库id、监控k8sid，监控网络id
    private String hostId;

    //机器类型,(1,主机,2.数据库,3.k8s,4.网络监控)
    private Integer machineType;

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public String getHostId() {
        return hostId;
    }

    public AlarmQuery setHostId(String hostId) {
        this.hostId = hostId;
        return this;
    }

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }
}
