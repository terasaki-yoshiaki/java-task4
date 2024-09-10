package com.Inventory.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

@Controller
public class accountController {

		InventoryDAO dao = new InventoryDAO();

		@PostMapping("/accountcreate")
		//DTOからDAOへ
		public String account(@ModelAttribute InventoryDTO dto, Model model) {
			if(dto != null ) {
	        //DAOを呼び出して処理
	        InventoryDAO InventoryDAO = new InventoryDAO();
	        InventoryDAO.insert(dto);
	        List<InventoryDTO> list = dao.select3();
			model.addAttribute("Inventory", list);
			return "accountlist.html";
		}
			return "account.html";
		}
}


