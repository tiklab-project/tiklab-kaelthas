package io.tiklab.kaelthas.kubernetes.kubernetesInfo.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class KuOverviewQuery {

    private List<Order> orderParams = OrderBuilders.instance().asc("createTime").get();

    private Page pageParam = new Page();

    private String kuId;

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

    public String getKuId() {
        return kuId;
    }

    public KuOverviewQuery setKuId(String kuId) {
        this.kuId = kuId;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
