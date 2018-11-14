package com.sds.inventory.api;

public class Item {
	
	private String itemName;
	private Integer sellIn;
	private Integer quality;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getSellIn() {
		return sellIn;
	}
	public void setSellIn(Integer sellIn) {
		this.sellIn = sellIn;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	public boolean daysExceedSellIn(Integer days) {
		return days > this.sellIn;
	}
	public boolean daysDoesntExceedSellIn(Integer days) {
		return days <= this.sellIn;
	}
	
	

}
