/*package com.Inventory.Controller;

	import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

	@Controller
	public class custamController {
		
		
		@PostMapping("/inventorycustom")
		//DTOからDAOへ
		public String editform(@ModelAttribute InventoryDTO dto, Model model) {
	        
	        //DAOを呼び出して処理
	       InventoryDAO InventoryDAO = new InventoryDAO();
	        InventoryDAO.update(dto);
	        //getMappingの処理を同じ処理をさせたい
	        return "redirect:/";
		}
	}*/

