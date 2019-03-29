package com.molinari.utility.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;


public class UtilIoTest {

	@Test
	public void testParent() {
		String dirFromFile = UtilIo.getParentPath(new File("C:/temp/ciao.txt"));
		assertEquals("Err!", "C:\\temp\\", dirFromFile);
	}

}
