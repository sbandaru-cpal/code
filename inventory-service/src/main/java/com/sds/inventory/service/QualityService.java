package com.sds.inventory.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.inventory.api.Item;
import com.sds.inventory.dao.ItemDAO;

@Service
public class QualityService {
	
	public static final int CONCERT_BACKSTAGE_PASSES_DOUBLE_CHARGE_WITHIN_DAYS = 10;

	private static final int MIN_ITEM_QUALITY = 0;

	public static final int MAX_ITEM_QUALITY = 50;

	private Set<String> specialItems = Stream.of("Aged Brie", "Sulfuras", "Concert backstage passes").collect(Collectors.toSet());
	
	@Autowired
	private ItemDAO itemDAO;
	
	public Integer calculateItemsValue(List<String> items, Integer days) {
		return items.stream().map(e -> calculateItemValue(itemDAO.getItem(e), days)).reduce(MIN_ITEM_QUALITY, Integer::sum);
	}

	private Integer calculateItemValue(Item item, Integer days) {
		return specialItems.contains(item.getItemName()) ? calculateSpecialItemValue(item, days) : calculateRegularItemValue(item, days);
	}

	private Integer calculateSpecialItemValue(Item item, Integer days) {
		if(StringUtils.equals("Aged Brie", item.getItemName())) {
			return calculateAgedBrieValue(item, days);
		}
		
		if(StringUtils.equals("Sulfuras", item.getItemName())) {
			return item.getQuality() >  MAX_ITEM_QUALITY ? MAX_ITEM_QUALITY : item.getQuality();
		}
		
		return calculateConcertBackStagePassesValue(item, days);
	}

	private Integer calculateConcertBackStagePassesValue(Item item, Integer days) {
		Integer sellInDays = item.getSellIn();
		if(item.daysExceedSellIn(days)) {
			return MIN_ITEM_QUALITY;
		}
		Integer quality = item.getQuality();
		Integer incrementBy = 1;
		Integer noOfDaysToSellInAfterCalculatedDay = sellInDays - days;
		if(noOfDaysToSellInAfterCalculatedDay >= CONCERT_BACKSTAGE_PASSES_DOUBLE_CHARGE_WITHIN_DAYS ) {
			return quality + incrementBy * days;
		}
		
		Integer doublePriceDays = CONCERT_BACKSTAGE_PASSES_DOUBLE_CHARGE_WITHIN_DAYS - (noOfDaysToSellInAfterCalculatedDay);
		Integer singlePriceDays = days - doublePriceDays;
		quality = quality + singlePriceDays + 2*incrementBy*doublePriceDays;
		return quality > MAX_ITEM_QUALITY ? MAX_ITEM_QUALITY : quality ;
	}

	private Integer calculateAgedBrieValue(Item item, Integer days) {
		Integer quality = item.getQuality();
		Integer sellInDays = item.getSellIn();
		Integer incrementsBy = 1;
		Integer decrementsBy = 1;
		if(item.daysDoesntExceedSellIn(days)) {
			return quality + incrementsBy*days;
		}
		else {
			int daysAfterSellIn = days - sellInDays;
			quality = quality + incrementsBy*sellInDays;
			quality = quality - 2* decrementsBy*daysAfterSellIn;
		}
		return quality < MIN_ITEM_QUALITY ? MIN_ITEM_QUALITY : quality > MAX_ITEM_QUALITY ? MAX_ITEM_QUALITY : quality;
	}

	private Integer calculateRegularItemValue(Item item, Integer days) {
		Integer quality = item.getQuality();
		Integer sellInDays = item.getSellIn();
		Integer decrementsBy = 1;
		if(item.daysDoesntExceedSellIn(days)) {
			return quality-decrementsBy*days;	
		}
		else {
			Integer daysAfterSellInDays = days - sellInDays;
			quality = quality - decrementsBy*sellInDays;
			quality = quality - 2*decrementsBy*daysAfterSellInDays;
		}
		
		return quality < MIN_ITEM_QUALITY ? MIN_ITEM_QUALITY : quality > MAX_ITEM_QUALITY ? MAX_ITEM_QUALITY : quality;
	}
	
}
