package jpabook.jpashop.dto;

import jpabook.jpashop.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
public class OrderReponseDto {
  private Long id;
  private Member member;
  private List<OrderItem> orderItems;
  private Delivery delivery;
  private OrderStatus status;
  private LocalDateTime orderDate;

  public static OrderReponseDto from(Order order) {
    return OrderReponseDto.builder()
            .id(order.getId())
            .member(order.getMember())
            .orderItems(order.getOrderItems())
            .status(order.getStatus())
            .orderDate(order.getOrderDate())
            .build();
  }
}
