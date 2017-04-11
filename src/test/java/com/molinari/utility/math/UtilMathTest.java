package com.molinari.utility.math;

import org.junit.Assert;
import org.junit.Test;
 
public class UtilMathTest {

	@Test
	public void arrotondaDecimaliDoubleTest(){
		Assert.assertEquals(35.0, UtilMath.arrotondaDecimaliDouble(34.667, 0), 0);
		Assert.assertEquals(34.7, UtilMath.arrotondaDecimaliDouble(34.667, 1), 0);
	}
}