/*package com.Inventory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Inventory.Model.DAO.InventoryDAO;
import com.Inventory.Model.DTO.InventoryDTO;

import ch.qos.logback.core.model.Model;


@Controller
public class InventoryController {
    InventoryDAO dao = new InventoryDAO();

    @PostMapping("/ic")
    public String index(Model model) {
        //List<InventoryDTO> list = dao.select();
        //model.addAttribute("Inventory", list);
        return "index.html";
    }
    
    @PostMapping("/form")
    public String form() {
        return "form.html";
    }
    
    @PostMapping("/editform")
    public String editfrom(Model model) {
       // List<InventoryDTO> list = dao.select();
       // model.addAttribute("Inventory", list);
        return "custom.html";
    }
    
    
    @PostMapping("/delete")
	//DTOからDAOへ
	public String delete(@ModelAttribute InventoryDTO dto, Model model) {
        
        //DAOを呼び出して処理
        InventoryDAO InventoryDAO = new InventoryDAO();
        InventoryDAO.delete(dto);
        //getMappingの処理を同じ処理をさせたい
        return "redirect:/";
    }
}
*/
