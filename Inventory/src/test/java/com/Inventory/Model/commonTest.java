package com.Inventory.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class commonTest {

	@Test
	void testjoin() {
		String str = null;
		str = common.join("結合","結果");
		assertEquals("結合結果",str);
	}

	@Test
	void testjoin2() {
		String str = null;
		str = common.join("結合","失敗");
		assertEquals("結合結果",str);
	}
	
	@Test
	void isNumber() {
		int a = 1;
		int b = 0;
		boolean A;
		A = common.isNumber(a,b);
		assertTrue(A);
	}
	
	@Test
	void isNumber2() {
		int a = 1;
		int b = 1;
		boolean A;
		A = common.isNumber(a,b);
		assertTrue(A);
	}
	
	@Test
	void isNumber3() {
		int a = 1;
		int b = 0;
		boolean A;
		A = common.isNumber(a,b);
		assertFalse(A);
	}
}

