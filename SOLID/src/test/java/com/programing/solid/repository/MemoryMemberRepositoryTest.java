package com.programing.solid.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.programing.solid.domain.member.Grade;
import com.programing.solid.domain.member.Member;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {
    HashMap<Long, Member> store = new HashMap<>();

    @BeforeEach
    void 기존회원_생성() {
        Member insertMember1 = new Member(1L, "MemberA", Grade.BASIC);
        store.put(1L, insertMember1);

        Member insertMember2 = new Member(2L, "MemberB", Grade.BASIC);
        store.put(2L, insertMember2);
    }

    @Test
    void 회원추가() {
        // given
        Member insertMember = new Member(3L, "Member", Grade.BASIC);

        // when
        store.put(3L, insertMember);
        Member findNewMember = store.get(3L);
        // then
        Assertions.assertThat(findNewMember).isEqualTo(insertMember);
    }

    @Test
    void 회원찾기() {
        // given
        Member findMember1 = store.get(1L);
        Member findMember2 = store.get(2L);

        // when

        // then
        Assertions.assertThat(findMember1.getName()).isEqualTo("MemberA");
        Assertions.assertThat(findMember2.getName()).isEqualTo("MemberB");
    }
}
