package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {
  @PersistenceContext EntityManager entityManager;
  @Autowired OrderService orderService;

  @Test
  @DisplayName("상품 주문이 가능하다.")
  void order() {
    Member member = createMember("A");
    Album album = createAlbum(20000, 10);

    entityManager.persist(member);
    entityManager.persist(album);

    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), album.getId(), orderCount);
    Order order = orderService.getOrder(orderId);

    assertEquals("주문 상태는 ORDER이다.", OrderStatus.ORDER, order.getStatus());
    assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, album.getStockQuantity());
  }

  @Test
  @DisplayName("주문을 취소할 수 있다.")
  void cancelOrder() {
    Member member = createMember("A");
    Album album = createAlbum(20000, 10);

    entityManager.persist(member);
    entityManager.persist(album);

    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), album.getId(), orderCount);
    orderService.cancelOrder(orderId);

    assertEquals("주문 상태는 CANCEL이다.", OrderStatus.CANCEL, orderService.getOrder(orderId).getStatus());
    assertEquals("재고가 정상적으로 복구된다.", 10, album.getStockQuantity());
  }

  @Test
  @DisplayName("재고 수량이 부족하면 주문할 수 없다.")
  void cannotOrderWithOverflowedQuantity() throws NotEnoughStockException {
    Member member = createMember("A");
    Album album = createAlbum(20000, 10);

    entityManager.persist(member);
    entityManager.persist(album);

    int orderCount = 11;
    assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), album.getId(), orderCount));
  }

  private Album createAlbum(int price, int stockQuantity) {
    Album album = new Album();
    album.setPrice(price);
    album.setStockQuantity(stockQuantity);
    return album;
  }

  private Member createMember(String name) {
    Member member = new Member();
    member.setName(name);
    member.setAddress(new Address("서울시", "잠실로", "1"));
    return member;
  }
}
