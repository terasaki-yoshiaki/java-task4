package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// Spring Testのためのセットアップ
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.ui.Model;

import com.Inventory.Controller.ListSearchController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SpringJUnitConfig
public class ListSearchTest4 extends DBTestCase {

    @InjectMocks
    private ListSearchController listSearchController;

    @Mock
    private InventoryDAO inventoryDAO;

    private IDatabaseTester databaseTester;

    @BeforeEach
    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/mysql",
            "root",
            "password"
        );
        IDataSet dataSet = getDataSet();
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        // ListSearch.xmlを読み込んでデータセットを作成
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("ListSearch.xml"));
    }

    @Test
    public void testListSearch() {
        InventoryDTO dto = new InventoryDTO();
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        ArrayList<InventoryDTO> expectedList = new ArrayList<>();
        InventoryDTO item = new InventoryDTO();
        item.setTradingDate(Date.valueOf("2024-09-19"));
        expectedList.add(item);

        when(inventoryDAO.select1(dto)).thenReturn(expectedList);

        Model model = mock(Model.class);
        String viewName = listSearchController.ListSearch(dto, model);

        assertEquals("ListSearch.html", viewName);
        verify(model).addAttribute("Inventory", expectedList);
    }

    @Test
    public void testInventoryList() throws Exception {
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");
        dto.setTradingDate(Date.valueOf("2024-09-19"));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("1");

        ArrayList<InventoryDTO> expectedList = new ArrayList<>();
        InventoryDTO item = new InventoryDTO();
        item.setJanCode("1234567890123");
        item.setProductName("Test Product");
        expectedList.add(item);

        when(inventoryDAO.select2(dto)).thenReturn(expectedList);

        Model model = mock(Model.class);
        String viewName = listSearchController.InventoryList(dto, model, request, response, session);

        assertEquals("InventoryList.html", viewName);
        verify(model).addAttribute("Inventory", expectedList);
    }

    @Test
    public void testInventoryListNoAuthority() throws Exception {
        InventoryDTO dto = new InventoryDTO();
        dto.setUserName("testUser");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Authority")).thenReturn("0");

        Model model = mock(Model.class);
        String viewName = listSearchController.InventoryList(dto, model, request, response, session);

        assertEquals("ListSearch.html", viewName);
        verify(model).addAttribute("ErrorMessage", "*閲覧権限がない為、表示できません");
    }
}
