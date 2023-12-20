package com.programing.solid;

import com.programing.solid.domain.DiscountPolicy;
import com.programing.solid.domain.FixDiscountPolicy;
import com.programing.solid.repository.MemberRepository;
import com.programing.solid.repository.MemoryMemberRepository;
import com.programing.solid.service.MemberService;
import com.programing.solid.service.MemberServiceImpl;
import com.programing.solid.service.OrderService;
import com.programing.solid.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy(memberRepository());
    }

    @Bean
    OrderService orderService() {
        return new OrderServiceImpl(discountPolicy());
    }

    @Bean
    MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

}
