package jpabook.jpashop.dto;

import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class OrderSearchDto {
  private String memberName = "";

  private OrderStatus orderStatus = OrderStatus.ORDER;
}
