package jpabook.jpashop.controller.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlbumForm extends ItemForm {
  private String artist;

  private String etc;
}
