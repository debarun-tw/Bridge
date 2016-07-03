package com.thoughtworks.kanjuice.restService.models;

import com.thoughtworks.kanjuice.restService.models.Order;

public class OrderBuilder {
    private final Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder withDefaults() {
        order.type = "tea";
        order.userID = "123";
        order.deviceID = "123";
        return this;
    }

    public OrderBuilder withType(String type) {
        order.type = type;
        return this;
    }

    public Order build() {
        return order;
    }
}
