package jpabook.jpashop.service;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;

  @Test
  @DisplayName("정상적으로 회원 가입이 가능하다.")
  void joinWithoutDuplicate() throws Exception {
    Member member = new Member();
    member.setName("A");

    var savedId = memberService.join(member);

    Assertions.assertThat(member).isEqualTo(memberService.getMember(savedId));
  }

  @Test
  @DisplayName("중복된 이름으로 회원 가입이 불가능하다.")
  void joinWithDuplicate() throws Exception {
    Member member1 = new Member();
    member1.setName("A");

    Member member2 = new Member();
    member2.setName("A");

    memberService.join(member1);
    assertThrows(IllegalStateException.class, () -> memberService.join(member2));
  }
}
