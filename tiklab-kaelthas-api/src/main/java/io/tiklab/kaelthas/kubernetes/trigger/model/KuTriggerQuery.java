package io.tiklab.kaelthas.kubernetes.trigger.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class KuTriggerQuery {

    //排序
    private List<Order> orderParams = OrderBuilders.instance().asc("createTime").get();


    //分页
    private Page pageParam = new Page();


     //触发器状态 1.已启用 2.未启用
    private int state;

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

    public int getState() {
        return state;
    }

    public KuTriggerQuery setState(int state) {
        this.state = state;
        return this;
    }
}