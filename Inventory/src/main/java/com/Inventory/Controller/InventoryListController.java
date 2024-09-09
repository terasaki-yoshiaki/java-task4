package com.Inventory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import ch.qos.logback.core.model.Model;

@Controller
public class InventoryListController {
	
	InventoryDAO dao = new InventoryDAO();

	@PostMapping("")
	
	//DTOからDAOへ
	public String index(@ModelAttribute InventoryDTO dto, Model model) {
		if(dto != null ) {
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.insert(dto);
    	   return "redirect:InventoryList.html";
		} else {
			return "redirect:InventoryList.html";
		}
	}

    @PostMapping("/InventoryCreate")
    public String InventoryCreate() {
        return "form.html";
    }
    
    
    @PostMapping("/Inventorydelete")
	//DTOからDAOへ
	public String delete(@ModelAttribute InventoryDTO dto, Model model) {
        
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.delete(dto);
        //getMappingの処理を同じ処理をさせたい
        return "redirect:/InventoryList.html";
    }
    }
    
    
    
    
    
    
    
    
    
    
    
    