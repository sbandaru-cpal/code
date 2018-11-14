package com.sds.inventory.dao;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sds.inventory.api.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemDAOTest {
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Test
	public void testGetItem() {
		Item item = itemDAO.getItem("Sulfuras");
		Integer expectedSellIn = 0;
		Integer expectedQuality = 80;
		assertEquals(expectedSellIn, item.getSellIn());
		assertEquals(expectedQuality, item.getQuality());

	}

}
