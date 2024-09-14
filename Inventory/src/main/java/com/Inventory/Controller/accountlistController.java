package com.Inventory.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

@Controller
public class accountlistController {
	InventoryDAO dao = new InventoryDAO();
	
	 @PostMapping("/acr")
	    public String acr() {
	        	return "account.html";
	 }
	
    @PostMapping("/aup")
   	//DTOからDAOへ
   	public String aup(@ModelAttribute InventoryDTO dto, Model model) {
           
           //DAOを呼び出して処理
          InventoryDAO InventoryDAO = new InventoryDAO();
           InventoryDAO.update2(dto);
           List<InventoryDTO> list = dao.select3();
			model.addAttribute("Inventory", list);
			return "accountlist.html";
		}
    
    @PostMapping("/ade")
	//DTOからDAOへ
	public String delete(@ModelAttribute InventoryDTO dto, Model model) {
        
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.delete(dto);
        List<InventoryDTO> list = dao.select3();
		model.addAttribute("Inventory", list);
		return "accountlist.html";
	}
    }
