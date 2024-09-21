package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Inventory.Controller.ListSearchController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ListSearchDBTest {

    @Autowired
    private ListSearchController listSeachController;

    private InventoryDAO dao;

    @BeforeEach  //各テストメソッドの前に実行される
    public void setUp() throws Exception {
        dao = new InventoryDAO();
        // DBUnitの設定
        //JdbcDatabaseTester を使ってデータベーステストを初期化
        IDatabaseTester databaseTester = new JdbcDatabaseTester(
        	//MySQLデータベースに接続するためのJDBCドライバー、URL、ユーザー名、パスワードを指定
            "com.mysql.cj.jdbc.Driver",   // JDBCドライバー
            "jdbc:mysql://localhost:3306/sample1",  // JDBC URL
            "root",  // ユーザー名
            "root"   // パスワード
        );
        
        //FlatXmlDataSetBuilder を使って、XML形式のテストデータを読み込み
        //test_data.xml からデータを取得
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("test_data.xml"));
        //読み込んだデータセットをデータベーステスターに設定
        databaseTester.setDataSet(dataSet);
        //テスト前にデータベースをクリーンアップし、新しいデータを挿入する操作を設定
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
      //テスト後に全てのデータを削除する操作を設定
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        //設定した内容に基づいてデータベースのセットアップを実行
        databaseTester.onSetup();
    }
    @Test
    public void testListSearch() throws Exception {
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19")); // テスト用の日付を指定

        List<InventoryDTO> result = dao.select1(dto);
        assertEquals(1, result.size()); // 期待される結果の行数を指定
        assertEquals(java.sql.Date.valueOf("2024-09-19"), result.get(0).getTradingDate()); // 期待される日付を確認
    }

    @Test
    public void testListSearchNoResults() throws Exception {
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-01-01")); // 存在しない日付を指定

        List<InventoryDTO> result = dao.select1(dto);
        assertEquals(0, result.size()); // 結果が空であることを確認
    }
}
