package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name= "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    // ORDINARY : 숫자
    // ** ORDINARY를 쓰게 되면 중간에 값이 추가되었을 때 문제가 될 수 있으므로 되도록이면 STRING 사용!
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
