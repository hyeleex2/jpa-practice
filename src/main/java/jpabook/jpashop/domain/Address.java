package jpabook.jpashop.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

// 어딘가 내장 가능
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
