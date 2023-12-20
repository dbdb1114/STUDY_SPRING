package com.programing.solid.domain;

import com.programing.solid.domain.member.Grade;
import com.programing.solid.domain.member.Member;
import com.programing.solid.repository.MemberRepository;
import com.programing.solid.repository.MemoryMemberRepository;

public class FixDiscountPolicy implements DiscountPolicy {

    MemberRepository memberRepository = new MemoryMemberRepository();
    int fixedDiscountAmount;

    @Override
    public int discountAmount(Order order) {
        Member member = memberRepository.findById(order.getMemberId());
        if (member.getGrade() == Grade.VIP) {
            return fixedDiscountAmount;
        } else {
            return 0;
        }
    }
}
