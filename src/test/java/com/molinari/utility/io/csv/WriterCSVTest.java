package com.molinari.utility.io.csv;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class WriterCSVTest {

	@Test
	public void test() throws IOException {
		String absolutePath = new File("target").getAbsolutePath();
		WriterCSV<BeanOperationFile> csv = new WriterCSV<>(BeanOperationFile.class, absolutePath + "/test.csv");
		BeanOperationFile b = new BeanOperationFile();
		b.setInput("input");
		b.setOutput("output");
		b.setNotes("nots");
		b.setError("err");
		List<BeanOperationFile> bean = Arrays.asList(b);
		csv.write(bean);
	}

}
