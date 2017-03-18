package main.java.com.molinari.utility.math.counters;

public interface Cifra {

	Cifra getCifraInferiore();
	Cifra getCifraSuperiore();
	void setCifraInferiore(Cifra sup);
	void setCifraSuperiore(Cifra inf);
	String getValore();
	void setValore(String string);
	
}
