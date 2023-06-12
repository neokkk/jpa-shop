package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.OrderSearchDto;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final MemberService memberService;
  private final ItemService itemService;

  @GetMapping("/orders")
  public String list(
      @ModelAttribute("orderSearch") OrderSearchDto orderSearch,
      Model model,
      @PageableDefault(size = 10) Pageable pageable) {
    List<Order> orders = orderService.findOrders(orderSearch, pageable);
    model.addAttribute("orders", orders);
    return "orders/orderList";
  }

  @GetMapping("/orders/new")
  public String create(Model model) {
    List<Member> members = memberService.findMembers();
    List<Item> items = itemService.findItems();

    model.addAttribute("members", members);
    model.addAttribute("items", items);

    return "orders/orderForm";
  }

  @PostMapping("/orders/new")
  public String create(@RequestParam("memberId") Long memberId,
                       @RequestParam("itemId") Long itemId,
                       @RequestParam("count") int count) {
    orderService.order(memberId, itemId, count);
    return "redirect:/orders";
  }

  @PostMapping("/orders/{orderId}/cancel")
  public String cancel(@PathVariable("orderId") Long orderId) {
    orderService.cancelOrder(orderId);
    return "redirect:/orders";
  }
}
