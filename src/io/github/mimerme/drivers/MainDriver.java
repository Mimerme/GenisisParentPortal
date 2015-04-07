package io.github.mimerme.drivers;

import java.util.Scanner;

import io.github.mimerme.parentportal.Portal;

public class MainDriver {
	public static void main(String[] args){
		String u, p, eLog;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter username");
		u = scanner.next();
		System.out.print("Enter password");
		p = scanner.next();
		
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
