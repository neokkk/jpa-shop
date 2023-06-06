package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    void testMember() {
        Member member = new Member();
        member.setName("A");

        Long memberId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(memberId);

        Assertions.assertThat(findMember).isEqualTo(member); // JPA 엔티티 동일성 보장
    }
}