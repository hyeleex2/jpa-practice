package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// protected로 인자 없는 생성자를 만드는 것과 동일.
// protected로 생성자를 만드는 경우, 외부에서 new 를 통해 생성자를 만들 수 없음.
// OrderItem 생성시, createOrderItem 으로만 생성 가능함.
public class OrderItem {
    
    @Id @GeneratedValue
    @Column(name= "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name= "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name= "order_id")
    private Order order;
    
    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비지니스 로직==/
    public void cancel() {
        // 재고 수량 원복
        getItem().addStock(this.count);
    }

    // ==조회 로직==//
    /** 주문상품 전체 가격 조회 **/
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
