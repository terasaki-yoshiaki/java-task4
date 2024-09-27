package com.Inventory.Model;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import com.Inventory.Controller.ListSearchController;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {YourSpringConfig.class}) // Springの設定クラスを指定
public class ListSearchDBTest5 extends DatabaseTestCase {

    private IDataSet backupDataSet; // 元のデータをバックアップするための変数
    
    @Autowired
    private ListSearchController listSearchController; // コントローラのインスタンス


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
    public void testListSearch() {
    	
    	//Modelインターフェースのモックオブジェクトを作成。これにより、テスト中にモデルの振る舞いを模倣できろ
        Model model = mock(Model.class);
        
        //InventoryDTOのインスタンスを作成し、検索する日付を設定
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19")); // 検索する日付を設定

        //コントローラのListSearchメソッドを実行し、その戻り値をviewNameに格納
        String viewName = listSearchController.ListSearch(dto, model);

        // 結果の検証
        //戻り値が期待されるビュー名"ListSearch.html"と一致するかを検証
        assertEquals("ListSearch.html", viewName);
        
        //モデルに"Inventory"という属性が追加されたことを確認する。属性の値は任意のリストであることを許可する。
        verify(model).addAttribute(eq("Inventory"), anyList()); // モデルにInventoryが追加されることを確認
    }
    
    
    @Test
    public void testDecisionWithAuthority() throws Exception {
    	//モデル、リクエスト、レスポンス、セッションのモックオブジェクトを作成
    	assertNotNull("listSearchController should not be null", listSearchController);
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // セッションの権限を設定
        //リクエストからセッションを取得するようにモックを設定し、セッションの権限を"1"（権限あり）に設定
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("1"); // 権限あり

        //InventoryDTOのインスタンスを作成し、ユーザー名と検索する日付を設定
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");
        dto.setTradingDate(java.sql.Date.valueOf("2024-09-19")); // 検索する日付を設定

        //コントローラのInventoryListメソッドを実行し、その戻り値をviewNameに格納
        String viewName = listSearchController.InventoryList(dto, model, request, response, session);

        // 結果の検証
        //戻り値が期待されるビュー名"InventoryList.html"と一致するかを検証
        assertEquals("InventoryList.html", viewName);
        
        //モデルに"Inventory"という属性が追加されたことを確認
        verify(model).addAttribute(eq("Inventory"), anyList()); // モデルにInventoryが追加されることを確認
    }

    @Test
    public void testDecisionWithoutAuthority() throws Exception {
    	assertNotNull("listSearchController should not be null", listSearchController);
    	//モデル、リクエスト、セッションのモックオブジェクトを作成
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        // セッションの権限を設定
        //リクエストからセッションを取得するようにモックを設定し、セッションの権限を"0"（権限なし）に設定
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("0"); // 権限なし

        //InventoryDTOのインスタンスを作成し、ユーザー名を"testUser"に設定
        //このDTOは、コントローラメソッドに渡されるデータを表している
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");

        //listSearchController.InventoryList(dto, model, request, null, session);を呼び出して
        //指定した引数でコントローラのInventoryListメソッドを実行します
        //戻り値はビュー名としてviewNameに格納
        String viewName = listSearchController.InventoryList(dto, model, request, null, session);

        //期待されるビュー名（"ListSearch.html"）と実際に取得したviewNameが一致するかどうかを検証する
        //これにより、権限がない場合の動作が正しいことを確認
        assertEquals("ListSearch.html", viewName);
        
        //モックされたmodelに対して、"ErrorMessage"という属性が追加されたことを確認する
        //この属性の値が"＊閲覧権限がない為、表示できません"であることを検証する
        //これにより、権限がない場合のエラーメッセージが正しく設定されることを確認する
        verify(model).addAttribute("ErrorMessage", "*閲覧権限がない為、表示できません");
    }
}

