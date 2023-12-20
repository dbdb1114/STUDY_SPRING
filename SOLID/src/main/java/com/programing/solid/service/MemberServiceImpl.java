package com.programing.solid.service;

import com.programing.solid.domain.member.Member;
import com.programing.solid.repository.MemberRepository;
import com.programing.solid.repository.MemoryMemberRepository;

public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }
}
