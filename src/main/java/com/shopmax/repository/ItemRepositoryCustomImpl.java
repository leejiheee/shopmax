package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopmax.constant.ItemSellStatus;
import com.shopmax.dto.ItemSearchDto;
import com.shopmax.entity.Item;
import com.shopmax.entity.QItem;

import jakarta.persistence.EntityManager;


public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	
	//현재 날짜로부터 이전날짜를 구해주는 메소드
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null;
		else if(StringUtils.equals("1d", searchDateType))
				dateTime = dateTime.minusDays(1); //현재 날짜로부터 1일 전
		else if(StringUtils.equals("1w", searchDateType))
			dateTime = dateTime.minusMonths(1); //현재날짜로부터 1주 전
		else if(StringUtils.equals("6m", searchDateType))
			dateTime = dateTime.minusMonths(6); // 현재 날짜로부터 6달 전

	
		return QItem.item.regTime.after(dateTime);
	}
	
	//상태를 전체로 했을 때 null이 들어있으므로 처리를 한번 해준다.
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("itemNm", searchBy)) {
			//등록자로 검색시
			return QItem.item.itemNm.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createBy", searchBy)) {
			return QItem.item.createBy.like("%" + searchQuery + "%");
		}
		return null;
	}
		
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		//select * from item where reg_time = ? and item_sell_status = ? and item_nm liike %검색어% oeder by item
		
		List<Item> content = queryFactory 
				.selectFrom(QItem.item)
				.where(regDtsAfter(itemSearchDto.getSearchDateType()), searchSellStatusEq(itemSearchDto.getSearchSellStatus()), 
						searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
				.orderBy(QItem.item.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch(); 
	
		//select count(*) from item where reg_time = ? and item_sell_status = ? and item_nm liike %검색어% oeder by item
		long total = queryFactory.select(Wildcard.count).from(QItem.item)
				.where(regDtsAfter(itemSearchDto.getSearchDateType()), searchSellStatusEq(itemSearchDto.getSearchSellStatus()), 
						searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
				.fetchOne();
		
		
		
		return new PageImpl<>(content, pageable, total);
	}
	
	
	
}
