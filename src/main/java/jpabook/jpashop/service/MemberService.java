package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepoistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final있는 필드만 생성자 주입
public class MemberService {

    private final MemberRepoistory repsository;

    @Transactional
    public Long join(Member member) {
        this.validateDuplicateMember(member);
        this.repsository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member) {
        // exception
        List<Member> findMembers = this.repsository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    public List<Member> findAll() {
        return this.repsository.findAll();
    }

    public Member findOne(Long id) {
        return this.repsository.findOne(id);
    }
}

