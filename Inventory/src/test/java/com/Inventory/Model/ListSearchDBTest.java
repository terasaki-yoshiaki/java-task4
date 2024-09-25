package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

@SpringBootTest
public class ListSearchDBTest {

    private InventoryDAO dao;

    // DatabaseDBTestクラスを外に出す
    static class DatabaseDBTest {
        private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mysql";
        private static final String USER = "root";
        private static final String PASSWORD = "root";

        public static void initializeDatabase() throws Exception {
            Class.forName(JDBC_DRIVER);
            IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);

            new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD).getConnection();
            IDataSet dataSet = new FlatXmlDataSetBuilder()
            	    .build(DatabaseDBTest.class.getClassLoader().getResourceAsStream("ListSearch2.xml"));

            databaseTester.setDataSet(dataSet);
            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
            databaseTester.onSetup();
            
        }
            
        
        
    @BeforeEach
    public void setUp() throws Exception {
        dao = new InventoryDAO();
        DatabaseDBTest.initializeDatabase(); // データベースの初期化を呼び出す
    }


    @Test
    public void testListSearch() throws Exception {
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19"));

        List<InventoryDTO> result = dao.select1(dto);
        assertEquals(1, result.size());
        assertEquals(java.sql.Date.valueOf("2024-09-19"), result.get(0).getTradingDate());
    }

    @Test
    public void testListSearchNoResults() throws Exception {
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-01-01"));

        List<InventoryDTO> result = dao.select1(dto);
        assertEquals(0, result.size());
    }
        }
    }

