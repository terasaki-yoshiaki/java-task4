package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
//java.sql.Date や ArrayList、List をインポートする。日付やリストを扱うために必要
import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//@InjectMocks はモックを注入する対象のクラスを示し、@Mock はモックオブジェクトを示す
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//Springフレームワークの Model インターフェイスをインポートする。コントローラからビューにデータを渡すために使用
import org.springframework.ui.Model;

import com.Inventory.Controller.ListSearchController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ListSearchTest {

    @InjectMocks
    //ListSeachController のインスタンスを作成し、その中にモックオブジェクトを注入
    private ListSearchController listSeachController;

    @Mock
    //InventoryDAO のモックオブジェクトを作成すろ。このモックは、データベース操作を模擬する
    private InventoryDAO dao;

    @Mock
    //Model のモックオブジェクトを作成する。コントローラがビューにデータを渡す際に使用
    private Model model;

    @BeforeEach
    //各テストメソッドの前に実行されるセットアップメソッド。Mockitoのモックオブジェクトを初期化
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDateSearch() {
        // Arrange
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        ArrayList<InventoryDTO> mockList = new ArrayList<>();
        InventoryDTO mockInventory = new InventoryDTO();
        mockInventory.setTradingDate(Date.valueOf("2024-09-19"));
        mockList.add(mockInventory);

        when(dao.select1(dto)).thenReturn(mockList);

        // モックオブジェクトの作成
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        
        // セッションに値を設定する場合
        when(request.getSession()).thenReturn(session);
        
        // Act
        String viewName = listSeachController.ListSearch(dto, model);

        // 結果の検証
        assertEquals("InventoryList.html", viewName);
        verify(model).addAttribute("Inventory", mockList);
    }


    @Test
    //日付検索で結果がない場合のテストメソッドを定義
    public void testDateSearchNoResults() {
        // Arrange
    	//テスト用の InventoryDTO オブジェクトを作成し、日付を設定
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        //dao.select1(dto) が呼ばれた場合に、空のリストを返すように設定
        when(dao.select1(dto)).thenReturn(new ArrayList<>());

        ListSearchController listSearchController = null;
		// Act
        //ListSeachController の ListSearch メソッドを呼び出し、その結果を result に格納
        @SuppressWarnings("null")
		String result = listSearchController.ListSearch(dto, model);

        // Assert
        //結果が "ListSearch.html" であることを確認
        assertEquals("ListSearch.html", result);
        //モックの model に対して、属性が追加されなかったことを確認
        verify(model, never()).addAttribute(anyString(), any());
    }

    @Test
    public void testSelectInventory() {
        // Arrange
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("validUser");
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        ArrayList<InventoryDTO> mockList = new ArrayList<>();
        InventoryDTO mockInventory = new InventoryDTO();
        mockInventory.setTradingDate(Date.valueOf("2024-09-19"));
        mockList.add(mockInventory);

        when(dao.select2(dto)).thenReturn(mockList);

        // Act
        String result = null;
		try {
			result = listSeachController.InventoryList(dto, model, null, null, null);
		} catch (ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        // Assert
        assertEquals("InventoryList.html", result);
        verify(model).addAttribute("Inventory", mockList);
    }

    @Test
    //ンベントリ選択機能のテストを行うためのテストメソッドを定義し
    public void testSelectInventoryNoAuthority() {
        // Arrange
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("validUser");
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        when(dao.select2(dto)).thenReturn(new ArrayList<>());

        // Act
        String result = null;
		try {
			result = listSeachController.InventoryList(dto, model, null, null, null);
		} catch (ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        // Assert
        assertEquals("ListSearch.html", result);
        verify(model).addAttribute("ErrorMessage", "*閲覧権限がない為、表示できません");
    }
}
