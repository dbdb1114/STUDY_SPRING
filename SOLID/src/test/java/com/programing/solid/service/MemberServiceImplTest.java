package com.programing.solid.service;

import static org.junit.jupiter.api.Assertions.*;

import com.programing.solid.domain.member.Grade;
import com.programing.solid.domain.member.Member;
import com.programing.solid.repository.MemberRepository;
import com.programing.solid.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceImplTest {
    MemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    void 기존회원_생성() {
        Member insertMember1 = new Member(1L, "MemberA", Grade.BASIC);
        memberRepository.save(insertMember1);

        Member insertMember2 = new Member(2L, "MemberB", Grade.BASIC);
        memberRepository.save(insertMember2);
    }

    @Test
    void join() {
        // given
        Member member = new Member(1L, "memberA", Grade.BASIC);

        // when
        memberRepository.save(member);

        // then
        Assertions.assertThat(memberRepository.findById(1L)).isEqualTo(member);
    }

    @Test
    void findMember() {
        // given
        Member findMember1 = memberRepository.findById(1L);
        Member findMember2 = memberRepository.findById(2L);

        // when

        // then
        Assertions.assertThat(findMember1.getName()).isEqualTo("MemberA");
        Assertions.assertThat(findMember2.getName()).isEqualTo("MemberB");
    }
}
