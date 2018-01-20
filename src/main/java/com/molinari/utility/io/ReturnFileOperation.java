package com.molinari.utility.io;

import java.util.ArrayList;
import java.util.List;

public class ReturnFileOperation {

	private String response;
	private List<String> errors = new ArrayList<>();
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
