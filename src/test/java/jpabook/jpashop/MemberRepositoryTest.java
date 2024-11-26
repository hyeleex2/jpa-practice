package jpabook.jpashop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember() throws  Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
//        Assertions.assertThat(findMember.getId(), member.getId());
//        Assertions.assertThat(findMember.getUsername(), member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member); // true
        // Transactinal로 묶인 같은 영속성 컨텍스트 안에서는 id값이 같으면 같은 entity로 인식


    }

}