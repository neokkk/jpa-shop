package jpabook.jpashop.controller.api;

import jpabook.jpashop.dto.OrderReponseDto;
import jpabook.jpashop.dto.OrderSearchDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("apiOrderController")
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<List<OrderReponseDto>> orders(
      OrderSearchDto orderSearch,
      @PageableDefault(size = 10) Pageable pageable
  ) {
    var orders = this.orderService.findOrders(orderSearch, pageable);
    return ResponseEntity.ok(orders.stream().map(OrderReponseDto::from).toList());
  }
}
