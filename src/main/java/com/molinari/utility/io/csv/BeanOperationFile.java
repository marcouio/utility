package com.molinari.utility.io.csv;

import com.opencsv.bean.CsvBind;

public class BeanOperationFile {

	@CsvBind
	private String input;
	
	@CsvBind
	private String output;
	
	@CsvBind
	private String notes;
	
	@CsvBind
	private String error;
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
