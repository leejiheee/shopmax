package com.shopmax.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopmax.dto.MainItemDto;
import com.shopmax.dto.ItemSearchDto;
import com.shopmax.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ItemService itemService;
	
	 @GetMapping(value="/") //localhost로 들어갔을 때
	 public String main (ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
		 
		 Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		 Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
				 
		  model.addAttribute("items", items);
		  model.addAttribute("itemSearchDto", itemSearchDto); //아직 사용X
	  
	  return "/main"; }
	 
	
	
}
