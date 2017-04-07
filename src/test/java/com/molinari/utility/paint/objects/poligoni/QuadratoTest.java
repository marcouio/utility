package com.molinari.utility.paint.objects.poligoni;

import org.junit.Test;

public class QuadratoTest {

	@Test
	public void testArea(){
		Rettangolo rettangolo = new Rettangolo("ret");
		Quadrato quadrato = new Quadrato();
		quadrato.setSize(50, 50);
		rettangolo.setSize(100, 50);
		rettangolo = quadrato;
		rettangolo.setSize(50, 20);
		
		org.junit.Assert.assertEquals(1000, rettangolo.getArea());
	}
}
