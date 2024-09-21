package com.Inventory.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;


@Controller

public class formController {
	 InventoryDAO dao = new InventoryDAO();
	 
	private static final String InventoryDTO = null;
	
	@PostMapping("/ic")
	
	//DTOからDAOへ
	public String form(@ModelAttribute InventoryDTO dto, Model model) {
		if(dto == null ) {
			return "form.html";
		} else {
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.insert1(dto);
		List<InventoryDTO> list = dao.select2(dto);
		model.addAttribute("Inventory", list);
			return "InventoryList.html";
	
		
}
	}


	@GetMapping("/back")
	public String back() {
		return "InventoryList.html";
}
}
