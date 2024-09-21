package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Inventory.Controller.indexController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class indexDBTest {

    @SuppressWarnings("unused")
	@Autowired  //依存性注入: indexController を自動的に注入,Springがこのクラスを管理
    private indexController indexController;

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

    @SuppressWarnings("unused")
    //データベース接続を取得するメソッドを宣言
	private IDatabaseConnection getConnection() throws Exception {
    	//JDBCを使ってMySQLデータベースに接続
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample1", "root", "root");
        //DatabaseConnection オブジェクトを作成して返す
        return new DatabaseConnection(connection);
    }

    @Test		//InventoryDAO のログイン機能をテストしている
    //ログイン成功時のテスト
    public void testLoginSuccess() throws Exception {
    	//InventoryDTO オブジェクトを作成し、ユーザー名とパスワードを設定
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("validUser");
        dto.setPassword("validPass");

        //dao.select(dto) を呼び出して、ユーザー情報を取得
        List<InventoryDTO> result = dao.select(dto);
        //結果のサイズが1であることを確認
        assertEquals(1, result.size());
        //取得したユーザー情報のユーザー名が "validUser" であることを確認
        assertEquals("validUser", result.get(0).getUserName());
    }

    @Test
    //ログイン失敗時のテスト
    public void testLoginFailure() throws Exception {
    	//InventoryDTO オブジェクトを作成し、無効なユーザー名とパスワードを設定
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("invalidUser");
        dto.setPassword("invalidPass");

        //dao.select(dto) を呼び出して、無効なユーザー情報を取得
        List<InventoryDTO> result = dao.select(dto);
        //結果のサイズが0であることを確認。無効なユーザーの場合、結果が存在しないことを期待
        assertEquals(0, result.size());
    }
}
