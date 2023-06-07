package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepository {
  private final EntityManager entityManager;

  public void save(Order order) {
    entityManager.persist(order);
  }

  public List<Order> findAll() {
    return entityManager.createQuery("select o from Order o", Order.class)
        .setMaxResults(1000)
        .getResultList();
  }

  public Order findOne(Long id) {
    return entityManager.find(Order.class, id);
  }

  public List<Order> search(OrderSearch orderSearch) {
    return entityManager.createQuery(
        "select o from Order o join o.member m" +
        " where o.status = :status" +
        " and m.name like :name", Order.class)
        .setParameter("status", orderSearch.getOrderStatus())
        .setParameter("name", orderSearch.getMemberName())
        .setMaxResults(1000)
        .getResultList();
  }
}
