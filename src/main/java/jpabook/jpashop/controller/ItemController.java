package jpabook.jpashop.controller;

import jpabook.jpashop.controller.item.AlbumForm;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  @GetMapping("/items/new")
  public String create(Model model) {
    model.addAttribute("item", new AlbumForm());
    return "items/itemForm";
  }

  @PostMapping("/items/new")
  public String create(AlbumForm albumForm, BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      Album album = Album.createAlbum(albumForm.getName(),
                                      albumForm.getPrice(),
                                      albumForm.getStockQuantity(),
                                      albumForm.getArtist(),
                                      albumForm.getEtc());
      itemService.save(album);
    }

    return "redirect:/items";
  }

  @GetMapping("/items/{itemId}/edit")
  public String edit(@PathVariable("itemId") Long id, Model model) {
    Album album = (Album)itemService.findItem(id);
    AlbumForm albumForm = new AlbumForm();
    albumForm.setId(album.getId());
    albumForm.setName(album.getName());
    albumForm.setPrice(album.getPrice());
    albumForm.setStockQuantity(album.getStockQuantity());
    albumForm.setArtist(album.getArtist());
    albumForm.setEtc(album.getEtc());
    model.addAttribute("item", albumForm);
    return "items/itemEditForm";
  }

  @PostMapping("/items/{itemId}/edit")
  public String edit(@ModelAttribute("item") AlbumForm albumForm) {
    Album album = Album.createAlbum(albumForm.getName(),
                                    albumForm.getPrice(),
                                    albumForm.getStockQuantity(),
                                    albumForm.getArtist(),
                                    albumForm.getEtc());
    album.setId(albumForm.getId());
    itemService.save(album);

    return "redirect:/items";
  }
}
