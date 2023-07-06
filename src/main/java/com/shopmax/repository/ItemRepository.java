package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.Item;

						//<해당 repository에서 사용할 Entity, Entity클래스의 기본키 타입>
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{
	//select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);
	
	//퀴즈1
	//select * from item where item_nm = ? and item_sell_status = ?
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);
	//퀴즈1-2
	List<Item> findByPriceBetween(int price1, int price2);
	//퀴즈1-3
	List<Item> findByRegTimeAfter(LocalDateTime localDateTime);
	//퀴즈1-4
	List<Item> findByItemSellStatusNotNull();
	//퀴즈1-5
	List<Item> findByItemDetailLike(String endDetail);
	//List<Item> findByItemDetailEndingWith(String endDetail);
	//퀴즈1-6
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	//퀴즈1-7
	List<Item> findByPriceLessThanOrderByPriceDesc(int price);
	
	
	
	//JPQL(non native 쿼리) -> 컬럼명, 테이블명은 반드시 entity 클래스 기준으로 작성한다.
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	//jPQL(native query) -> h2 데이터베이스를 기준으로 쿼리문작성
	@Query(value="select * from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetil); 
	
	//퀴즈 2-1
	@Query("select i from Item i where i.price >= :price")
	List<Item> getPrice(@Param("price") int price);
	
	//퀴즈 2-2
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :itemSellStatus")
	List<Item> getItemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("itemSellStatus") ItemSellStatus itemSellStatus);
	
}
