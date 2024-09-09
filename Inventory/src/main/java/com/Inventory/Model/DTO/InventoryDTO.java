package com.Inventory.Model.DTO;

import java.sql.Date;

import lombok.Data;

//InventoryDTOクラス*/
	@Data
	public class InventoryDTO {
	    private int ID;
	    private String UserName;
	    private String Password;
	    private int I ;
	    private String JanCode;
	    private String ProductName;
	    private Date TradingDate;
	    private int Price;
	    private int InventoryBuy;
	    private int InventorySell;
	    private int Profit;
	    private String Authority;
		}
	

