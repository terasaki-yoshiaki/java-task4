package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

public class DBTest {

    private IDatabaseTester databaseTester;
    private InventoryDAO inventoryDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // DBUnitのデータベース接続設定
        databaseTester = new JdbcDatabaseTester(
            "com.mysql.cj.jdbc.Driver", 
            "jdbc:mysql://localhost:3306/mysql", "root", "root"
        );

        // テスト用データセットの読み込み
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/ListSearch2.xml"));
        databaseTester.setDataSet(dataSet);

        // データベースの初期化（DBUnitのセットアップ）
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        // DAOのインスタンスを生成
        inventoryDAO = new InventoryDAO();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // DBUnitでのデータベースのクリーンアップ
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    @Test
    public void testSelect1ByDate() {
        // InventoryDTOのテスト用インスタンスを作成
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19")); // 日付をセット

        // DAOのselect1メソッドを呼び出し、結果を取得
        ArrayList<InventoryDTO> result = inventoryDAO.select1(dto);

        // 検証
        assertNotNull(result);
        assertEquals(1, result.size()); // 期待される結果のサイズ
        assertEquals(java.sql.Date.valueOf("2024-09-19"), result.get(0).getTradingDate()); // 日付の確認
    }

    @Test
    public void testSelect2ProductDetails() {
        // InventoryDTOのテスト用インスタンスを作成
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19")); // 日付をセット

        // DAOのselect2メソッドを呼び出し、結果を取得
        ArrayList<InventoryDTO> result = inventoryDAO.select2(dto);

        // 検証
        assertNotNull(result);
        assertEquals(1, result.size()); // 期待される結果のサイズ
        InventoryDTO product = result.get(0);
        
        // 取得した商品の詳細を確認
        assertEquals("123456789", product.getJanCode());
        assertEquals("Sample Product", product.getProductName());
        assertEquals(1000, product.getPrice());
        assertEquals(500, product.getInventoryBuy());
        assertEquals(300, product.getInventorySell());
        assertEquals(200, product.getProfit());
    }
}
