package com.Inventory.Model;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.Inventory.Controller.ListSearchController; // コントローラクラスのインポート
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ListSearchDBTest3 {

    private IDatabaseTester databaseTester;
    
	private ListSearchController listSearchController; // コントローラのインスタンス

    @BeforeEach
    public void setUp() throws Exception {
        new InventoryDAO();
        listSearchController = new ListSearchController();

        // DBUnitの設定
        databaseTester = new JdbcDatabaseTester(
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/mysql",
            "root",
            "root"
        );
        
     // スキーマを設定（必要に応じて変更）
       // databaseTester.setSchema("mysql"); // 正しいスキーマ名に変更

     // スキーマが「mysql」の場合、スキーマ名を明示的に指定
        IDatabaseConnection connection = databaseTester.getConnection();
     // スキーマ付きの完全修飾テーブル名を使えるように設定
        connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());

        //connection.setSchema("mysql");

        //databaseTester.setSchema("mysql"); // スキーマを明示的に設定
        
        //ListSearch.xmlというXMLファイルからデータセットを作成します。これにより、テストデータをデータベースに挿入
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("ListSearch2.xml"));
        //データセットをテストオブジェクトに設定
        databaseTester.setDataSet(dataSet);
        //テスト実行前にデータベースをクリアし、新しいデータを挿入する操作を指定
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
      //テスト後は全てのデータを削除
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        //設定した内容に基づいてデータベースのセットアップを実行
        databaseTester.onSetup();
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
