package io.tiklab.kaelthas.kubernetes.monitor.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class KuMonitorQuery {

    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

    private Page pageParam = new Page();

    private String kuId;


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

    public String getKuId() {
        return kuId;
    }

    public KuMonitorQuery setKuId(String kuId) {
        this.kuId = kuId;
        return this;
    }

}
