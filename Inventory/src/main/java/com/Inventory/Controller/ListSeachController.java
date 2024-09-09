package com.Inventory.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

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
				return "redirect:/";
			}
}


		@PostMapping("/decision")
		public String InvetoryList(Model model) {
			List<InventoryDTO> list = dao.select2();
			model.addAttribute("Inventory", list);
			return "InventoryList.html";
		}
}