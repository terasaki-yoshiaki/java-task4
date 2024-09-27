package com.Inventory.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {YourSpringConfig.class}) // Springの設定クラスを指定
public class ListSearchDBTest6 extends DatabaseTestCase {

    private IDataSet backupDataSet; // 元のデータをバックアップするための変数
    
    private final InventoryDAO dao = new InventoryDAO();
    
//    @Autowired
//    private ListSearchController listSearchController; // コントローラのインスタンス


    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        Connection jdbcConnection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mysql", // データベース名
            "root", // ユーザー名
            "root"  // パスワード
        );
        IDatabaseConnection con = new DatabaseConnection(jdbcConnection, "mysql");
        con.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        return con;
    }

    @Override
    protected void setUp() throws Exception {
        IDatabaseConnection connection = getConnection();
        
        // 元のデータをバックアップ
        backupDataSet = connection.createDataSet(new String[] { "product_detail" });
        
        // 親クラスのsetUp()を呼び出して、テスト用データの挿入を行う
        super.setUp();
    }


    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(
            getClass().getClassLoader().getResourceAsStream("ListSearch2.xml")
        );
    }

    

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT; // データを追加する
    }

    @Override
    protected void tearDown() throws Exception {
        IDatabaseConnection connection = getConnection();

//        try {
//            // まず、テーブルをクリア
//            DatabaseOperation.DELETE_ALL.execute(connection, getDataSet());
//        } catch (Exception e) {
//            System.err.println("Error clearing data: " + e.getMessage());
//        }

        try {
            // バックアップデータを元に戻す
            DatabaseOperation.CLEAN_INSERT.execute(connection, backupDataSet);
        } catch (Exception e) {
            System.err.println("Error restoring backup data: " + e.getMessage());
        }

        super.tearDown();
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

    
    