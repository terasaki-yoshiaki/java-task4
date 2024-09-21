package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.Inventory.Controller.indexController;
import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

public class indexTest {

    @InjectMocks  //モックオブジェクトを注入する
    private indexController indexController;

    @Mock  //モックオブジェクトを作成する
    private InventoryDAO dao;

    private InventoryDTO validUser;
    private InventoryDTO invalidUser;

    @BeforeEach  //各テストメソッドの前に実行される
    public void setUp() {
    	
    	// Mockitoのモックオブジェクトを初期化
        MockitoAnnotations.openMocks(this);
        
        //validUser オブジェクトを作成し、ユーザー名、パスワード、権限を設定
        validUser = new InventoryDTO();  //有効なユーザーの初期化
        validUser.setUserName("testUser");
        validUser.setPassword("testPass");
        validUser.setAuthority(1); // 権限を設定

        //invalidUser オブジェクトを作成し、ユーザー名とパスワードを設定
        invalidUser = new InventoryDTO();  //無効なユーザーの初期化
        invalidUser.setUserName("invalidUser");
        invalidUser.setPassword("invalidPass");
    }

    @Test
    public void testLoginSuccess() throws Exception {  //ログイン成功時のテスト
        // Mockの設定
    	//dao.select(validUser) が呼ばれたときに、userList を返すように設定
        ArrayList<InventoryDTO> userList = new ArrayList<>();
        userList.add(validUser);
        when(dao.select(validUser)).thenReturn(userList);

        Model model = mock(Model.class);  //Model インターフェースのモックオブジェクトを作成
        String result = indexController.index(validUser, model, null, null, null);  //indexController の index メソッドを呼び出し、結果を result に格納

        assertEquals("ListSearch.html", result);  //result が "ListSearch.html" であることを確認
        verify(model, never()).addAttribute(eq("ErrorMessage"), anyString());  //model に "ErrorMessage" 属性が追加されていないことを確認
    }

    @Test
    public void testLoginFailure() throws Exception {  //ログイン失敗時のテスト
        // Mockの設定
    	//dao.select(invalidUser) が呼ばれたときに、空のリストを返すように設定
        when(dao.select(invalidUser)).thenReturn(new ArrayList<>());

        Model model = mock(Model.class);  //Model インターフェースのモックオブジェクトを作成
        String result = indexController.index(invalidUser, model, null, null, null);  //indexController の index メソッドを呼び出し、結果を result に格納

        assertEquals("index.html", result);  //result が "index.html" であることを確認
        verify(model).addAttribute("ErrorMessage", "*ユーザー名もしくはパスワードが一致しません");  //model に "ErrorMessage" 属性が追加されていることを確認し、その内容が指定されたエラーメッセージであることを検証
    }
}
