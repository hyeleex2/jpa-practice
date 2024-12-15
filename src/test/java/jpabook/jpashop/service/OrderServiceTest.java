package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {

        // given
        Member member = createMember();
        Book book = createBook("책임", 1000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문 시 상태는 order");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(1000 * orderCount, getOrder.getTotalPrice(), "주문 가격");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다.");
    }


    @Test()
    public void 상품주문_재고수량초과() throws Exception {
        assertThrows(NotEnoughStockException.class, () -> {
            // given
            Member member = createMember();
            Book book = createBook("책임", 1000, 10);

            int orderCount = 11;

            // when
            orderService.order(member.getId(), book.getId(), orderCount);

            // then
            fail("재고 수량 부족 예외가 발생해야 한다.");
        });
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("책임", 1000, 10);

        int orderCount = 2;
        Long longId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(longId);

        // then
        Order order = orderRepository.findOne(longId);
        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문 상태가 취소");
        assertEquals(10, item.getStockQuantity(), "재고 수량 원복");
    }


    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "은평", "123-123"));
        em.persist(member);
        return member;
    }
}