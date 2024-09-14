package com.Inventory.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ListSeachController {
	 InventoryDAO dao = new InventoryDAO();

	    @PostMapping("/listselect")
	    public String ListSearch(@ModelAttribute InventoryDTO dto, Model model) {
	        List<InventoryDTO> list = dao.select1(dto);
	        model.addAttribute("Inventory", list);
	        String TD =String.valueOf(dto.getTradingDate());
	        if(TD != null) {
	        	return "ListSearch.html";
	        } else {
				return "redirect:ListSearch.html";
			}
}


		@PostMapping("/decision")
		public String InventoryList(@ModelAttribute InventoryDTO dto, Model model,HttpServletRequest request, HttpServletResponse response,HttpSession httpSession)  throws ServletException, IOException {
	        String un = dto.getUserName();
			 HttpSession session = request.getSession();
		        String A =String.valueOf(session.getAttribute("Authority"));
		        if(A.equals("1")) {
			List<InventoryDTO> list = dao.select2(dto);
			model.addAttribute("Inventory", list);
			return "InventoryList.html";
		        } else {
		        	model.addAttribute("ErrorMessage","*閲覧権限がない為、表示できません");
					return "ListSearch.html";
		        }
		}
		

}
