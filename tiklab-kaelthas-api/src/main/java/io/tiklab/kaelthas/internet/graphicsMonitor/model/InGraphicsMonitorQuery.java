package io.tiklab.kaelthas.internet.graphicsMonitor.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class InGraphicsMonitorQuery {

    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

    private Page pageParam = new Page();

    private String internetId;

    private String type;

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

    public String getInternetId() {
        return internetId;
    }

    public InGraphicsMonitorQuery setInternetId(String internetId) {
        this.internetId = internetId;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
