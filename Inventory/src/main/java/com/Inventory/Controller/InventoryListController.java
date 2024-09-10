package com.Inventory.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

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

   
    @PostMapping("/IC")
    public String index() {
        	return "form.html";
		}
    
    @PostMapping("/iup")
   	//DTOからDAOへ
   	public String IU(@ModelAttribute InventoryDTO dto, Model model) {
           
           //DAOを呼び出して処理
          InventoryDAO InventoryDAO = new InventoryDAO();
           InventoryDAO.update(dto);
           List<InventoryDTO> list = dao.select2();
			model.addAttribute("Inventory", list);
			return "InventoryList.html";
		}
   	
    
    @PostMapping("/ide")
	//DTOからDAOへ
	public String IDe(@ModelAttribute InventoryDTO dto, Model model) {
        
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.delete1(dto);
        List<InventoryDTO> list = dao.select2();
		model.addAttribute("Inventory", list);
		return "InventoryList.html";
	}
    }
    