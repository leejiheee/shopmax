package com.shopmax.dto;

import com.shopmax.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ItemSearchDto {
	private String searchDateType;
	private ItemSellStatus searchSellStatus;
	private String searchBy;
	private String searchQuery = "";
	
}
