package com.Inventory.Controller;



import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@SessionAttributes

public class indexController {
	   InventoryDAO dao = new InventoryDAO();
	@PostMapping("/login")
		//DTOからDAOへ
		public String index(@ModelAttribute InventoryDTO dto, Model model, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) throws ServletException, IOException {
	        String un = dto.getUserName();
			//String pw = dto.getPassword();
			List<InventoryDTO> list = dao.select(dto);
	        model.addAttribute("dto",list);
	        int a = list.size();
	        if(a != 0) {
	        InventoryDTO A = list.get(0);
	       
	        String A2 = String.valueOf(A.getUserName());    
	        String sA = String.valueOf(A.getAuthority());
	      //セッションのインスタンス
	        HttpSession session = request.getSession();
	      //listに格納された権限データ
	       // String sessionA = request.getParameter(sA);
	        session.getId();
	        session.setAttribute("Authority",sA);
	        //request.getRequestDispatcher("/ListSearch.html").forward(request, response);
	        

	        if(un.equals(A2)) {
	        	return "ListSearch.html";
	        } else {
	        	model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しない、又は管理者権限がありません");
				return "index.html";
			}
		}
	        model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しません");
			return "index.html";
		}
	       
	   
		@PostMapping("/account")
	    public String account(@ModelAttribute InventoryDTO dto, Model model) {
	        	List<InventoryDTO> list = dao.select4(dto);
		        model.addAttribute("dto",list);
		        int b = list.size();
		        if(b != 0) {
		        InventoryDTO B = list.get(0);
		        String B2 = String.valueOf(B.getAuthority());
		        if(B2.equals("1")) {
	    	
		        List<InventoryDTO> list1 = dao.select3();
		        model.addAttribute("Inventory", list1);
	        	return "accountlist.html";
		        } else {
		        	model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しない、又は管理者権限がありません");
			        return "index.html";
			    }
		        }
		        model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しない、又は管理者権限がありません");
		        return "index.html";
	    }
	    
	    
	    
		
		@GetMapping("/")
	    public String index() {
	        	return "index.html";
			}
}
