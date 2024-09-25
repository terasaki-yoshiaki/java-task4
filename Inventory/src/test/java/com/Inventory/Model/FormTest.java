package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

public class FormTest {

    private IDatabaseConnection databaseConnection;
    private InventoryDAO inventoryDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // DB接続設定
        Connection jdbcConnection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mysql", "root", "root"
        );
        databaseConnection = new DatabaseConnection(jdbcConnection);

        // テスト用データセットの読み込み
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/ListSearch2.xml"));
        
        // データベースの初期化（DBUnitのセットアップ）
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);

        // DAOのインスタンスを生成
        inventoryDAO = new InventoryDAO();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // DBUnitでのデータベースのクリーンアップ
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/ListSearch2.xml"));
        DatabaseOperation.DELETE_ALL.execute(databaseConnection, dataSet);
        databaseConnection.getConnection().close(); // 接続を閉じる
    }

    @Test
    public void testRegisterProduct() {
        // 新しい商品を登録するためのDTOを作成
        InventoryDTO dto = new InventoryDTO();
        dto.setJanCode("987654321");
        dto.setProductName("New Sample Product");
        dto.setPrice(1500);
        dto.setInventoryBuy(100);
        dto.setInventorySell(50);
        dto.setProfit(100);
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19"));

        // 商品登録メソッドを呼び出し
        inventoryDAO.insert1(dto);

        // 登録された商品を確認するために、select2メソッドを呼び出す
        ArrayList<InventoryDTO> result = inventoryDAO.select2(dto);

        // 検証
        assertNotNull(result);
        assertEquals(1, result.size()); // 期待される結果のサイズ
        InventoryDTO registeredProduct = result.get(0);
        
        // 登録された商品の詳細を確認
        assertEquals("987654321", registeredProduct.getJanCode());
        assertEquals("New Sample Product", registeredProduct.getProductName());
        assertEquals(1500, registeredProduct.getPrice());
        assertEquals(100, registeredProduct.getInventoryBuy());
        assertEquals(50, registeredProduct.getInventorySell());
        assertEquals(100, registeredProduct.getProfit());
    }
}
