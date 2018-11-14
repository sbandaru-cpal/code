package com.sds.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.sds.inventory.api.Item;
import com.sds.inventory.util.DataBaseUtil;

import exception.ConnectionCloseException;

@Repository
public class ItemDAO {
	
	public Item getItem(String itemName)  {
		Connection connection = DataBaseUtil.getConnection();
		PreparedStatement selectPreparedStatement = null;
		Item item = new Item();
		String selectQuery = "select * from INVENTORY where item = ?";
		try {
			selectPreparedStatement = connection.prepareStatement(selectQuery);
			selectPreparedStatement.setString(1, itemName);
			ResultSet rs = selectPreparedStatement.executeQuery();
			item.setItemName(itemName);
			if (rs.next()) {
				item.setSellIn(rs.getInt("sell_in"));
				item.setQuality(rs.getInt("quality"));
			}

		} catch (SQLException e) {
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionCloseException();
		} finally {
			DataBaseUtil.closeConnection(connection);
		}
		return item;
	}

}
