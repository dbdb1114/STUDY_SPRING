package com.programing.solid.repository;

import com.programing.solid.domain.member.Grade;
import com.programing.solid.domain.member.Member;

public interface MemberRepository {
    void save(Member member);

    Member findById(Long id);
}
