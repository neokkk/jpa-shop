package jpabook.jpashop.controller.item;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {
  private Long id;

  @NotEmpty(message = "이름을 입력해야 합니다.")
  private String name;

  @NotEmpty(message = "가격을 입력해야 합니다.")
  private int price;

  @NotEmpty(message = "수량을 입력해야 합니다.")
  private int stockQuantity;
}
