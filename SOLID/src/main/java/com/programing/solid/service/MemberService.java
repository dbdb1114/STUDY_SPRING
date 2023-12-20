package com.programing.solid.service;

import com.programing.solid.domain.member.Member;

public interface MemberService {
    void join(Member member);

    Member findMember(Long id);
}
