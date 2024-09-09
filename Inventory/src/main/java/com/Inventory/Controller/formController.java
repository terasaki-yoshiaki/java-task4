/*package com.Inventory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import ch.qos.logback.core.model.Model;

@Controller
public class formController {

	private static final String InventoryDTO = null;
	
	@PostMapping("/ic")
	
	//DTOからDAOへ
	public String form(@ModelAttribute InventoryDTO dto, Model model) {
		if(dto != null ) {
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.insert1(dto);
        //getMappingの処理を同じ処理をさせたい
		} else {
			return "redirect:InventoryList.html";
		}
        return "Inventory.html";
	}

/*
	@PostMapping("/back")
	public String back() {
    return ("'welcome'");
	}
}
*/