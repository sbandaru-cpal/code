package com.sds.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.tools.DeleteDbFiles;

import exception.ConnectionCloseException;


public class DataBaseUtil {
	public static final String DB_DRIVER = "org.h2.Driver";
	public static final String DB_CONNECTION = "jdbc:h2:~/test";
	public static final String DB_USER = "";
	public static final String DB_PASSWORD = "";
	
	public static void deleteDatabase() throws SQLException {
		DeleteDbFiles.execute("~", "test", true);
	}

	public static Connection getConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectionCloseException();
		}
	}

	public static void prepareH2DB() throws SQLException {
		createInventoryTable();		
		insertData();
	}
	
	private static void insertData() throws SQLException {
		Connection connection = DataBaseUtil.getConnection();
		PreparedStatement insertPreparedStatement = null;
		String insertQuery = "INSERT INTO INVENTORY" + "(item, sell_in, quality) values" + "(?,?,?)";
		try {
			connection.setAutoCommit(false);
			insertPreparedStatement = connection.prepareStatement(insertQuery);
			insertPreparedStatement.setString(1, "+5 Dexterity Vest");
			insertPreparedStatement.setInt(2, 10);
			insertPreparedStatement.setInt(3, 20);
			insertPreparedStatement.executeUpdate();

			insertPreparedStatement = connection.prepareStatement(insertQuery);
			insertPreparedStatement.setString(1, "Aged Brie");
			insertPreparedStatement.setInt(2, 2);
			insertPreparedStatement.setInt(3, 0);
			insertPreparedStatement.executeUpdate();

			insertPreparedStatement = connection.prepareStatement(insertQuery);
			insertPreparedStatement.setString(1, "Elixir of the Mongoose");
			insertPreparedStatement.setInt(2, 5);
			insertPreparedStatement.setInt(3, 7);
			insertPreparedStatement.executeUpdate();

			insertPreparedStatement = connection.prepareStatement(insertQuery);
			insertPreparedStatement.setString(1, "Sulfuras");
			insertPreparedStatement.setInt(2, 0);
			insertPreparedStatement.setInt(3, 80);
			insertPreparedStatement.executeUpdate();
			
			insertPreparedStatement = connection.prepareStatement(insertQuery);
			insertPreparedStatement.setString(1, "Concert backstage passes");
			insertPreparedStatement.setInt(2, 15);
			insertPreparedStatement.setInt(3, 20);
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	private static void createInventoryTable() throws SQLException {
		Connection connection = DataBaseUtil.getConnection();
		PreparedStatement createPreparedStatement = null;		
		String createHoldingTable = "CREATE TABLE INVENTORY(item VARCHAR(100) primary key, sell_in INT NOT NULL, quality INT NOT NULL)";
		try {
			connection.setAutoCommit(false);
			createPreparedStatement = connection.prepareStatement(createHoldingTable);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			connection.commit();
		} catch (SQLException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

}
