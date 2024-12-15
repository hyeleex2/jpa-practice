package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepoistory;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepoistory memberRepoistory;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepoistory.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // order 를 persist 하면 orderItem, delivery 엔티티를 모두 persist 함 (cascade 옵션 때문)
        // cascade : 주문이 주문상품, 배송을 관리하는 식의 형태. 주문상품과 배송이 각각 주문만 참조하는 경우에만 쓰는 게 좋음.
        // private owner 일 경우에만 cascade 를 사용 하면 좋음.
        // 다른 엔티티에서 delivery, orderItem을 참조하는 경우에는 cascade를 사용 안하는 게 좋음.
        orderRepository.save(order);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        // 엔티티 안에 있는 데이터만 바꾸면,
        // JPA가 더티 체킹(변경된 내역 감지) 해서 바뀐 것만 바꿔주는 쿼리를 날려줌.
        order.cancel();
    }

    // 검색
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return this.orderRepository
//    }

}
