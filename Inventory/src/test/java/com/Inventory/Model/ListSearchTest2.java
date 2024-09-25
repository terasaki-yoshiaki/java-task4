package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.Inventory.Controller.ListSearchController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@ExtendWith(MockitoExtension.class)
public class ListSearchTest2 {

	private AutoCloseable closeable;
    @InjectMocks
    private ListSearchController listSearchController;

    @Mock
    private InventoryDAO inventoryDAO;

    @Mock
    private Model model;

    
    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
    	closeable.close();
    }

    @Test
    public void testListSearch() {
        // テストデータの準備
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(Date.valueOf("2024-09-19")); // 検索する日付

        ArrayList<InventoryDTO> expectedList = new ArrayList<>();
        InventoryDTO item = new InventoryDTO();
        item.setTradingDate(Date.valueOf("2024-09-19"));
        expectedList.add(item);

        // DAOのメソッドが呼ばれたときの戻り値を設定
        when(inventoryDAO.select1(dto)).thenReturn(expectedList);

        // メソッドの実行
        String viewName = listSearchController.ListSearch(dto, model);

        // 結果の検証
        assertEquals("ListSearch.html", viewName);
        verify(model).addAttribute("Inventory", expectedList);
    }

    @Test
    public void testInventoryList() throws Exception {
        // テストデータの準備
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // セッションの権限を設定
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("1"); // 管理者権限

        ArrayList<InventoryDTO> expectedList = new ArrayList<>();
        InventoryDTO item = new InventoryDTO();
        item.setJanCode("1234567890123");
        item.setProductName("Test Product");
        expectedList.add(item);

        // DAOのメソッドが呼ばれたときの戻り値を設定
        when(inventoryDAO.select2(dto)).thenReturn(expectedList);

        // メソッドの実行
        String viewName = listSearchController.InventoryList(dto, model, request, response, session);

        // 結果の検証
        assertEquals("InventoryList.html", viewName);
        verify(model).addAttribute("Inventory", expectedList);
    }

    @Test
    public void testInventoryListNoAuthority() throws Exception {
        // テストデータの準備
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // セッションの権限を設定
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("0"); // 権限なし

        // メソッドの実行
        String viewName = listSearchController.InventoryList(dto, model, request, response, session);

        // 結果の検証
        assertEquals("ListSearch.html", viewName);
        verify(model).addAttribute("ErrorMessage", "*閲覧権限がない為、表示できません");
    }
}
