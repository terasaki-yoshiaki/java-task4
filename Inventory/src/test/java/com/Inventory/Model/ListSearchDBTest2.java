package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.Inventory.Controller.ListSearchController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ListSearchDBTest2 {

    private IDatabaseTester databaseTester;
    
    private InventoryDAO dao;
    
    @BeforeEach  //各テストメソッドの前に実行される
    public void setUp() throws Exception {
        dao = new InventoryDAO();
        // DBUnitの設定
        //JdbcDatabaseTester を使ってデータベーステストを初期化
        IDatabaseTester databaseTester = new JdbcDatabaseTester(
        	//MySQLデータベースに接続するためのJDBCドライバー、URL、ユーザー名、パスワードを指定
            "com.mysql.cj.jdbc.Driver",   // JDBCドライバー
            "jdbc:mysql://localhost:3306/mysql",  // JDBC URL
            "root",  // ユーザー名
            "root"   // パスワード
        );
        
        //FlatXmlDataSetBuilder を使って、XML形式のテストデータを読み込み
        //test_data.xml からデータを取得
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("ListSearch.xml"));
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
    public void testListSearch() {
        // テスト対象のコントローラを作成
        ListSearchController controller = new ListSearchController();
        Model model = mock(Model.class);

        // 検索する日付を設定
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        // メソッドの実行
        String viewName = controller.ListSearch(dto, model);

        // 結果の検証
        assertEquals("ListSearch.html", viewName);

        // モデルに追加されたデータの検証
        verify(model).addAttribute(eq("Inventory"), anyList());
    }

    @Test
    public void testInventoryList() throws Exception {
        // テスト対象のコントローラを作成
        ListSearchController controller = new ListSearchController();
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // セッションの権限を設定
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("1"); // 管理者権限

        // 検索する日付を設定
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        // メソッドの実行
        String viewName = controller.InventoryList(dto, model, request, response, session);

        // 結果の検証
        assertEquals("InventoryList.html", viewName);
        verify(model).addAttribute(eq("Inventory"), anyList());
    }
}
