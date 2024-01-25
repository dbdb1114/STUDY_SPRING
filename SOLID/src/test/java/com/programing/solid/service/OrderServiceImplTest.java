package com.programing.solid.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.programing.solid.domain.DiscountPolicy;
import com.programing.solid.domain.FixDiscountPolicy;
import com.programing.solid.domain.Order;
import com.programing.solid.domain.member.Grade;
import com.programing.solid.domain.member.Member;
import com.programing.solid.repository.MemberRepository;
import com.programing.solid.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {

    DiscountPolicy discountPolicy;
    MemberRepository memberRepository;


    @BeforeEach
    void 회원추가() {
        Member memberA = new Member(1L, "memberA", Grade.BASIC);
        Member memberB = new Member(2L, "memberB", Grade.VIP);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
    }

    @Test
    void 주문생성_테스트() {
        //given
        Order orderA = new Order(1L, "JPA 완전정복", 10000);
        Order orderB = new Order(2L, "토비의 스프링", 20000);

        System.out.println(memberRepository.findById(orderA.getMemberId()).getGrade());
        //when
        int ADiscount = discountPolicy.discountAmount(orderA);
        int BDiscount = discountPolicy.discountAmount(orderB);

        //then
        assertThat(ADiscount).isEqualTo(0);
        assertThat(BDiscount).isEqualTo(1000);
    }
}
