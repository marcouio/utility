package com.molinari.utility.text;

import java.time.format.DateTimeParseException;

import org.junit.Assert;
import org.junit.Test;

public class UtilTextTest {

	@Test(expected=DateTimeParseException.class)
	public void testCheckDateInException() {
		UtilText.checkDate("pippo", "dd/MM/yyyy");
	}
	
	@Test
	public void testCheckDate() {
		boolean checkDate = UtilText.checkDate("11/11/2011", "dd/MM/yyyy");
		Assert.assertTrue(checkDate);
	}

}
