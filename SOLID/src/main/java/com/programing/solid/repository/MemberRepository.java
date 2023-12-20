package com.programing.solid.repository;

import com.programing.solid.domain.member.Member;

public interface MemberRepository {
    void save();

    Member findById();
}
