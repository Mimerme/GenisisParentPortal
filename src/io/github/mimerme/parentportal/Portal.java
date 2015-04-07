package io.github.mimerme.parentportal;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.mimerme.interfaces.AssessmentInterface;
import io.github.mimerme.parentportal.Teacher;

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
			//Notice, genisis requires cookies to login
			//Specifically the JSESSIONID
		response = Jsoup.connect(LOGIN_URL)
			.userAgent("Mozila")
			.data("j_password", password)
			.data("j_username", username)
			.method(Method.POST)
			.cookie("JSESSIONID", sessionId)
			.execute();
		
		if(response.body().contains("Invalid user name or password"))
			return "Invalid username or password";
			
			parseResponse();
			return null;
		}
		catch(IOException e){
			e.printStackTrace();
			return "Unspecified Connection Error";
		}
		
	}
	
	private GradeBook parseResponse(){
		Document doc = Jsoup.parse(response.body());
		Elements elementClasses = doc.getElementsByClass("listroweven");
		elementClasses.addAll(doc.getElementsByClass("listrowodd"));
		
		Class[] classes = new Class[elementClasses.size()];
		
		Element element;
		
		//Don't look @ me I'm hideous!!
		for (int i = 0; i < classes.length; i++) {
			element = elementClasses.get(i);
			
			if(element.getElementsByTag("u").first() == null){
				continue;
			}
			
			classes[i] = new Class(new Course(element.getElementsByTag("u").text(), element.getElementsByClass("cellRight")
					.first()
					.getElementsByClass("cellRight")
					.first().text(), null),
					new Teacher(element.getElementsByClass("cellLeft").get(1).text().replace("Email:", "")
							, element.getElementsByClass("cellLeft").get(1).getElementsByTag("a").attr("href").replace("mailto:", "")));
			System.out.println(classes[i]);
		}
		
		getAssessmenets("asda", "asda", "asdas");
		
		return null;
	}
	
	//date in MM/DD/YYYY
	private AssessmentInterface[] getAssessmenets(String course, String date, String markingPeriod, String studentID){
		try {
			Document document = Jsoup.parse(Jsoup.connect("https://parents.edison.k12.nj.us/genesis/"
					+ "parents?module=gradebook&"
					+ "studentid=&"  + studentID
					+ "action=list&"
					+ "dateRange=" + markingPeriod
					+ "&date&" + date
					+ "courseAndSection=" + course)
					.cookie("JSESSIONID", response.cookie("JSESSIONID"))
					.execute().body());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String parseTagOut(String tag, String string){
		string = string.replace("<" + tag  + ">", "");
		string = string.replace("</" + tag + ">", "");
		return string;
	}
	
	public GradeBook getGradeBook(){return gradeBook;}
}
