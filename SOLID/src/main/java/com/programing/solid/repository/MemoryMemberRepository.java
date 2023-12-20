package com.programing.solid.repository;

import com.programing.solid.domain.member.Grade;
import com.programing.solid.domain.member.Member;
import java.util.HashMap;

public class MemoryMemberRepository implements MemberRepository {
    static HashMap<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long id) {
        Member member = store.get(id);
        return member;
    }
}
