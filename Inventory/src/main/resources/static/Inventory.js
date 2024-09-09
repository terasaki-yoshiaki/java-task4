/**
 * 
 */
function check() {
	//alert('登録ボタン押されたよ');
	let result1 = todoCheck();
	document.getElementById("validate_msg1").innerHTML = result1;
	let result2 = placeCheck();
	document.getElementById("validate_msg2").innerHTML = result2;
	let result3 = datetimeCheck();
	document.getElementById("validate_msg3").innerHTML = result3;
	if (result1 != "" || result2 != "" || result3 != "") {
		return false;
	}
	return true;
}
 //if (.isEmpty()) {
   //   System.out.println("空です。");
    //}
    //else {
      //System.out.println(str);
    //}


function todoCheck() {
	let validate = "";
	
	if (document.getElementById("Todo").value == "") {
		validate = "必須項目です。入力してください。";
		alert('登録ボタン押された1');
	}
	return validate;
}

function placeCheck() {
	let validate = "";
	if (document.getElementById("Place").value =="") {
		alert('登録ボタン押された2');
		validate = "必須項目です。入力してください。";
	}
	return validate;
}
function datetimeCheck() {
	let validate = "";
	if (document.getElementById("Datetime").value =="") {
		alert('登録ボタン押された3');
		validate = "必須項目です。入力してください。";
	}
	alert('登録ボタン押されたよ');
	return validate;
}