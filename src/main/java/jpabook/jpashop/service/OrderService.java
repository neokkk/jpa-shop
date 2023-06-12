package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.OrderSearchDto;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderService {
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;
  private final OrderRepository orderRepository;

  @Transactional
  public Long order(Long memberId, Long itemId, int count) throws IllegalStateException {
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    if (member == null) {
      throw new IllegalStateException("회원이 존재하지 않습니다.");
    }
    if (item == null) {
      throw new IllegalStateException("상품이 존재하지 않습니다.");
    }

    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    Order order = Order.createOrder(member, delivery, orderItem);
    orderRepository.save(order);
    return order.getId();
  }

  public List<Order> findOrders(OrderSearchDto orderSearch, Pageable pageable) {
    return orderRepository.search(orderSearch, pageable);
  }

  public Order getOrder(Long id) {
    return orderRepository.findOne(id);
  }

  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    order.cancel();
  }
}
