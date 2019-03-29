package com.molinari.utility.math.counters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlphanumericCounter {
 
	private List<Cifra> cifraList = new ArrayList<>();

	private static String stringCodes = "0123456789abcdefghilmnopqrstuvzABCDEFGHILMNOPQRSTUVZ*#@Â§?.,'!£$&/()";
	private String[] codes = {};
	private String current;
	
	public AlphanumericCounter(String code) {
		
		initCode();
		
		if(code != null){
			setCurrent(code);
			
			fillList(code);
			
			configCifre();
		}
	}

	private void initCode() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < stringCodes.length(); i++) {
			list.add(stringCodes.substring(i, i+1));
		}
		codes = list.toArray(new String[list.size()]);
	}
	
	public String getNext(){

		for (int i = 0; i < current.length(); i++) {
 			Cifra cifra = cifraList.get(i);
			cifra.setValore(current.substring(i, i+1));
		}
		
		updateValue(cifraList.get(cifraList.size()-1));

		current = createReturn();
		
		return current;


	}

	private String createReturn() {
		StringBuilder sb = new StringBuilder("");
		
		for (Cifra cifra : cifraList) {
			sb.append(cifra.getValore());
		}
		
		return sb.toString();
	}

	private void configCifre() {
		for (int i = 0; i < cifraList.size(); i++) {
			Cifra cifra = cifraList.get(i);
			if(i == 0){
				cifra.setCifraInferiore(cifraList.get(i+1));
			}else if(i==cifraList.size()-1){
				cifra.setCifraSuperiore(cifraList.get(i-1));
			}else{
				cifra.setCifraInferiore(cifraList.get(i+1));
				cifra.setCifraSuperiore(cifraList.get(i-1));
			}
		}
	}

	private void fillList(String code) {
		cifraList.clear();
		for (int i = 0; i < code.length(); i++) {
			CifraBase cifra = new CifraBase(code.substring(i, i+1));
			cifraList.add(cifra);
		}
	}
	
	public static void main(String[] args) {
		Date start = new Date();
		String next = "0000";
		AlphanumericCounter alphanumericCounter = new AlphanumericCounter(next);

		for (int i = 0; i < 15000000; i++) {
			next = alphanumericCounter.getNext();
		}
		System.out.println(next);
		Date end = new Date();
		
		long millisDiff = end.getTime()-start.getTime();
		
		// Calculates days/hours/minutes/seconds.
		int seconds = (int) (millisDiff / 1000 % 60);
		int minutes = (int) (millisDiff / 60000 % 60);
		
		System.out.println("minuti-secondi: "+minutes+"-"+seconds);
		System.out.println("millisecondi: " + millisDiff);
	}

	private void updateValue(Cifra cQuarto) {
		int index = trovaCifra(cQuarto);
		if(index != -1){
			if(index == getCodes().length-1){
				cQuarto.setValore(getCodes()[0]);
				Cifra cifraSuperiore = cQuarto.getCifraSuperiore();
				updateValue(cifraSuperiore);
				
			}else{
				cQuarto.setValore(getCodes()[index+1]);
			}
		}
	}

	private int trovaCifra(Cifra quattro) {
		for (int i = 0; i < getCodes().length; i++) {
			String code = getCodes()[i];
			if(code.equals(quattro.getValore())){
				return i;
			}
		}
		return -1;
		
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}
}
