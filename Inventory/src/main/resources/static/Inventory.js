/**
 * 
 */
function check() {
	alert('登録ボタン押されたよ');
	let result0 = TradingDateCheck();
	document.getElementById("validate_msg").innerHTML = result0;
	let result1 = ProductNameCheck();
	document.getElementById("validate_msg1").innerHTML = result1;
	let result2 = JanCodeCheck();
	document.getElementById("validate_msg2").innerHTML = result2;
	let result3 = PriceCheck();
	document.getElementById("validate_msg3").innerHTML = result3;
	let result4 = InventoryBuyCheck();
	document.getElementById("validate_msg4").innerHTML = result4;
	let result5 = InventorySellCheck();
	document.getElementById("validate_msg5").innerHTML = result5;
	let result6 = ProfitCheck();
	document.getElementById("validate_msg6").innerHTML = result6;
	alert('チェック2');
	/*
	if(document.getElementById("TradingDate").value == "") {
		flag = 1;
		} else if(document.getElementById("ProductName").value =="") {
			flag = 1;
			} else if(document.getElementById("JanCode").value =="") {
				flag = 1;
				} else if(document.getElementById("Price").value == "") {
					flag = 1;
					}else if(document.getElementById("InventoryBuy").value =="") {
						flag = 1;
						} else if(document.getElementById("InventorySell").value =="") {
							flag = 1;
							} else if (document.getElementById("Profit").value =="") {
								flag = 1;
								}
								
								if(flag){
									alert('必須項目に未入力がありました');
									return false;
								} else {
									return true;
								}*/
	if (result0 != "" && result1 != "" && result2 != "" && result3 != "" && result4 != "" && result5 != "" && result6 != "") {
		alert('通過');
		return true;	
	} 
		alert('停止');
	return false;
}
 alert('チェック');
function TradingDateCheck() {
	let validate = "";
	
	if (document.getElementById("TradingDate").value == "") {
		validate = "*登録日が未記入です";
		//alert('登録ボタン押された');
	}
	return validate;
}

function ProductNameCheck() {
	let validate = "";
	if (document.getElementById("ProductName").value =="") {
		//alert('登録ボタン押された1');
		validate = "*商品名が未記入です";
	}
	return validate;
}

function JanCodeCheck() {
	let validate = "";
	if (document.getElementById("JanCode").value =="") {
		//alert('登録ボタン押された2');
		validate = "*JANコードが未記入です";
	}
	return validate;
}
function PriceCheck() {
	let validate = "";
	
	if (document.getElementById("Price").value == "") {
		validate = "*仕入れ価格が未記入です";
		//alert('登録ボタン押された3');
		
	}
	return validate;
}

function InventoryBuyCheck() {
	let validate = "";
	if (document.getElementById("InventoryBuy").value =="") {
		//alert('登録ボタン押された4');
		validate = "*仕入れ数量が未記入です";
	}
	return validate;
}

function InventorySellCheck() {
	let validate = "";
	if (document.getElementById("InventorySell").value =="") {
		//alert('登録ボタン押された5');
		validate = "*販売数量が未記入です";
	}
	return validate;
}

function ProfitCheck() {
	let validate = "";
	if (document.getElementById("Profit").value =="") {
		//alert('登録ボタン押された6');
		validate = "*利益が未記入です";
	}
	//alert('登録ボタン押されたよ');
	return validate;
}