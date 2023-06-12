package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.dto.OrderSearchDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepository {
  private final EntityManager entityManager;
  private final JPAQueryFactory jpaQueryFactory;

  public void save(Order order) {
    entityManager.persist(order);
  }

  public Order findOne(Long id) {
    return entityManager.find(Order.class, id);
  }

  public List<Order> search(OrderSearchDto orderSearch, Pageable pageable) {
    return jpaQueryFactory
        .selectFrom(QOrder.order)
        .leftJoin(QOrder.order.orderItems, QOrderItem.orderItem).fetchJoin()
        .leftJoin(QOrder.order.delivery, QDelivery.delivery).fetchJoin()
        .leftJoin(QOrder.order.member, QMember.member).fetchJoin()
        .where(
            statusEq(orderSearch),
            memberNameEq(orderSearch)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private BooleanExpression statusEq(OrderSearchDto orderSearch) {
    var orderStatus = orderSearch.getOrderStatus();
    return orderStatus != null ? QOrder.order.status.eq(orderStatus) : null;
  }

  private BooleanExpression memberNameEq(OrderSearchDto orderSearch) {
    var memberName = orderSearch.getMemberName();
    return memberName != null ? QOrder.order.member.name.eq(memberName) : null;
  }
}
