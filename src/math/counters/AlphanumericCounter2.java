package math.counters;

import java.util.ArrayList;
import java.util.List;

public class AlphanumericCounter2 {
 
	private List<Cifra> cifraList = new ArrayList<>();

	private static String stringCodes = "0123456789abcdefghilmnopqrstuvzABCDEFGHILMNOPQRSTUVZ*#@§?.,'!£$&/()";
	private String[] codes = {};
	private String current;
	
	public enum Posizione{
		PRIMO, SECONDO, TERZO, QUATTRO
	}
	
	public AlphanumericCounter2(String code) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < stringCodes.length(); i++) {
			list.add(stringCodes.substring(i, i+1));
		}
		codes = list.toArray(new String[list.size()]);
		if(code != null){
			setCurrent(code);
			
			fillList(code);
			
			configCifre();
		}
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
			if(i ==0){
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
		String next = "0000";
		AlphanumericCounter2 alphanumericCounter = new AlphanumericCounter2(next);

		for (int i = 0; i < 15000000; i++) {
			next = alphanumericCounter.getNext();
		}
		System.out.println(next);
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
