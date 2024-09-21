package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    //日付検索機能のテストを行うためのテストメソッドを定義
    public void testDateSearch() {
        // Arrange
    	//モックリストを作成し、その中に日付を設定した InventoryDTO オブジェクトを追加
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(Date.valueOf("2024-09-19"));

     // List<InventoryDTO> 型で定義
        ArrayList<InventoryDTO> mockList = new ArrayList<>();
        InventoryDTO mockInventory = new InventoryDTO();
        mockInventory.setTradingDate(Date.valueOf("2024-09-19"));
        mockList.add(mockInventory);
        
        //dao.select1(dto) が呼ばれた場合に、先ほど作成した mockList を返すように設定
        when(dao.select1(dto)).thenReturn(mockList);

        // Act
     // メソッドの実行
        String viewName = listSearchController.InventoryList(dto, model, request, response, session);

        // Assert
        //結果が "ListSearch.html" であることを確認
        assertEquals("ListSearch.html", viewName);
        //モックの model に対して、"Inventory" という属性名で mockList が追加されたことを確認
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

        // Act
        //ListSeachController の ListSearch メソッドを呼び出し、その結果を result に格納
        String result = istSearchController.ListSearch(dto, model);

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
			result = listSearchController.InventoryList(dto, model, null, null, null);
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
			result = listSearchController.InventoryList(dto, model, null, null, null);
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
