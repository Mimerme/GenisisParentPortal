package io.github.mimerme.parentportal;

import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Portal {
	private final String LOGIN_URL = 
			"https://parents.edison.k12.nj.us/genesis/j_security_check";
	private Response response;
	
	private String username;
	private String password;
	
	private GradeBook gradeBook;
	
	public Portal(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public String connect(){
		try{
			//first connection to get cookies needed
			response = Jsoup.connect(LOGIN_URL).execute();
			String sessionId = response.cookie("JSESSIONID");
			System.out.println(sessionId);
			//Notice, genisis requires cookies to login
			//Specifically the JSESSIONID
		response = Jsoup.connect(LOGIN_URL)
			.userAgent("Mozila")
			.data("j_password", password)
			.data("j_username", username)
			.method(Method.POST)
			.cookie("JSESSIONID", sessionId)
			.execute();

		
		System.out.println(response.body());
		
		if(response.body().contains("Invalid user name or password"))
			return "Invalid username or password";
			
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
