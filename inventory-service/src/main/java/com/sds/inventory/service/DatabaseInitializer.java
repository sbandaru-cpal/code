package com.sds.inventory.service;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.sds.inventory.util.DataBaseUtil;

@Component
public class DatabaseInitializer {

	@PostConstruct
    public void init() throws SQLException{
		DataBaseUtil.deleteDatabase();
		DataBaseUtil.prepareH2DB();
    }
}
