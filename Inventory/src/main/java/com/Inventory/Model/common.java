package com.Inventory.Model;

public class common {

	public static String join(String str1,String str2) {
	StringBuilder sb = new StringBuilder();
	sb.append(str1);
	sb.append(str2);
	return sb.toString();
}
	
	public static boolean isNumber(int a,int b) {
if(a==b) {
	return true;
} else {
	return false;
}
	}
}
