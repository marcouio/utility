package com.molinari.utility.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HidePasswordFromCommandLine extends Thread {
	boolean stopThread = false;
	boolean hideInput = false;
	boolean shortMomentGone = false;

	public void run() {
		try {
			sleep(500);
		} catch (InterruptedException e) {
		}
		shortMomentGone = true;
		while (!stopThread) {
			if (hideInput) {
				System.out.print("\b*");
			}
			try {
				sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void main(String[] arguments) {
		String name = "";
		String password = "";
		HidePasswordFromCommandLine hideThread = new HidePasswordFromCommandLine();
		hideThread.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Name: ");
			// Aspetta l'immissione dello username e pulisce il buffer della
			// tastiera
			do {
				name = in.readLine();
			} while (hideThread.shortMomentGone == false);
			//
			// Nasconde il thread e sovrascrive l'input con "*"
			hideThread.hideInput = true;
			// Legge la password
			System.out.println("\nPassword:");
			System.out.print(" ");
			password = in.readLine();
			hideThread.stopThread = true;
		} catch (Exception e) {
		}
		System.out.print("\b \b");
		// Solo per testing, eliminare!
		System.out.println("\n\nLogin= " + name);
		System.out.println("Password= " + password);
	}
}
