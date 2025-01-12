package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    // application 로딩 시점에 호출
    // PostConstruct : spring bean이 다 올라오면 호출 됨.
    @PostConstruct
    public void init() {
        initService.doInit1();
        initService.doInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void doInit1() {
            Member member = createMember("userA", "서울", "은평구", "123");
            em.persist(member);

            Book book1 = createBook("jpa1", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("jpa2", 20000, 100);
            em.persist(book2);

            // 주문
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);
        }


        public void doInit2() {
            Member member = createMember("userB", "서울", "마포구", "123");
            em.persist(member);

            Book book1 = createBook("spring1", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("spring2", 40000, 300);
            em.persist(book2);

            // 주문
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);
        }

        private Member createMember(String name, String address1, String address2, String address3) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(address1, address2, address3));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

    }
}


