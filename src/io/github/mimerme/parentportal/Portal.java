package io.github.mimerme.parentportal;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Portal {
	private final String LOGIN_URL = 
			"https://parents.edison.k12.nj.us/genesis/j_security_check";
	private Connection.Response response;
	
	private String username;
	private String password;
	
	private GradeBook gradeBook;
	
	public Portal(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public String connect(){
		try{
		response = Jsoup.connect(LOGIN_URL)
			.data("j_username", username)
			.data("j_password", password)
			.execute();
			return null;
		}
		catch(IOException e){
			e.printStackTrace();
			return "Unspecified Connection Error";
		}
	}
	
	private GradeBook parseResponse(){
		
		return null;
	}
	
	public GradeBook getGradeBook(){return gradeBook;}
}
