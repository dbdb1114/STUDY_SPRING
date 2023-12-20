package com.programing.solid.service;

import com.programing.solid.domain.DiscountPolicy;
import com.programing.solid.domain.FixDiscountPolicy;
import com.programing.solid.domain.Order;

public class OrderServiceImpl implements OrderService {
    DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Order order) {

        // 할인금액 확인 후 Order 초기화
        int discountAmount = discountPolicy.discountAmount(order);
        order.setDiscountAmount(discountAmount);

        return order;
    }
}
