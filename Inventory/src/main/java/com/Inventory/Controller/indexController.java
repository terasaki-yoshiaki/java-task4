package com.Inventory.Controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

@Controller
public class indexController {
	   InventoryDAO dao = new InventoryDAO();
	   
		@PostMapping("/login")
		//DTOからDAOへ
		public String index(@ModelAttribute InventoryDTO dto, Model model) {
	        String un = dto.getUserName();
			//String pw = dto.getPassword();
			List<InventoryDTO> list = dao.select(dto);
	        model.addAttribute("dto",list);
	        String UN =dto.getUserName();
	        if(UN.equals(un)) {
	        	return "ListSearch.html";
	        } else {
				return "redirect:/";
			}
		}
		
	    @PostMapping("/account")
	    public String account(@ModelAttribute InventoryDTO dto, Model model) {
	        List<InventoryDTO> list = dao.select3();
	        model.addAttribute("Inventory", list);
	        	return "accountlist.html";
			}
}
