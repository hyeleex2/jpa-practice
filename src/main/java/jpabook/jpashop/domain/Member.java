package jpabook.jpashop.domain;

import jakarta.persistence.*;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded
    // Embedded 아니면 Embadable 둘 중 하나만 있어도 되긴 함..
    private Address address;

    @OneToMany(mappedBy = "member")
    // order 테이블에 있는 member 필드에 의해 맵핑됨
    // mappedBy 는 읽기 전용. 주인이 아님.
    private List<Order> orders = new ArrayList<>();

}
