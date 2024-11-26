package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
// 저장시에 DB에서 구분할 수 있는 값 설정
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;

    private String isbn;

}
