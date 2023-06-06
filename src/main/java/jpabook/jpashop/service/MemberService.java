package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
  @Autowired private MemberRepository memberRepository;

  public Long join(Member member) throws Exception {
    validateDuplicateMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  public Member getMember(Long memberId) {
    return memberRepository.findOne(memberId);
  }

  private void validateDuplicateMember(Member member) throws Exception {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }
}
