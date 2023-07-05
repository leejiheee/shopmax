package com.shopmax.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shopmax.dto.ItemFormDto;
import com.shopmax.entity.Item;
import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;
import com.shopmax.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepository;
	
	//item 테이블에 상품등록(insert)
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		
		//1. 상품등록
		Item item = itemFormDto.createItem(); //dto -> entity
		itemRepository.save(item); //insert(저장)
		
		//2. 이미지 등록
		for(int i= 0; i<itemImgFileList.size(); i++) {
			//fk키 사용 시 부모테이블에 해당하는 entity를 먼저 넣어줘야한다.
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			
			if(i == 0) {
				itemImg.setRepimgYn("Y");
			}else {   
				itemImg.setRepimgYn("N");
				}
			
				itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
			}
		
			return item.getId(); //등록한 상품 id을 리턴
			
		}
	}
	
