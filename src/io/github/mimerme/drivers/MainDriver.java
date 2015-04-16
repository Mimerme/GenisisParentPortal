package io.github.mimerme.drivers;

import io.github.mimerme.parentportal.Portal;

import java.io.Console;
import java.util.Scanner;

public class MainDriver {
	public static void main(String[] args){
		Console console = System.console();
		
	    if (console == null) {
	        System.out.println("Couldn't get Console instance,"
	        		+ " you may be running in the IDE. "
	        		+ "Password will be shown when you type");
	    }
		
		String u, p, eLog;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter username");
		u = scanner.next();
		
		if(console != null){
			char passwordArray[] = console.readPassword("Enter password");
			p = new String(passwordArray);
		}
		else{
			System.out.println("Enter password");
			p = scanner.next();
		}
		
		Portal portal = new Portal(u, p);
		
		eLog = portal.connect();
		if(eLog != null){
			System.out.println(eLog);
		}
		else{
			System.out.println("Login was successful");
		}
		//System.out.println();
	}
}
