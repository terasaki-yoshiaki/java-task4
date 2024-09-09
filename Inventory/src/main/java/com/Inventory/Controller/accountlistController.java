/*package com.Inventory.Controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

public class accountlistController {
	InventoryDAO dao = new InventoryDAO();
	
	@PostMapping("/accountcreate")
	//DTOからDAOへ
	public String account(@ModelAttribute InventoryDTO dto, Model model) {
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
	
	
    @PostMapping("/accountupdate")
    public String index() {
        return "form.html";
    }
    
    
    
    @GetMapping("/accountdelete")
	//DTOからDAOへ
	public String delete(@ModelAttribute InventoryDTO dto, Model model) {
        
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.delete(dto);
        //getMappingの処理を同じ処理をさせたい
        return "redirect:/InventoryList.html";
    }
    }*/
