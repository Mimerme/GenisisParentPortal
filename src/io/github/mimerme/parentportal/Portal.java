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
	
	Response studentResponse;
	public static String sessionId;
	
	private final String HOME_URL = 
			"https://parents.edison.k12.nj.us/genesis/parents?module=home";
	
	private final String LOGIN_URL = 
			"https://parents.edison.k12.nj.us/genesis/j_security_check";
	
	private String username;
	private String password;
		
	public Portal(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public String connect(){
		try{
			//first connection to get cookies needed
			Response response = Jsoup.connect(LOGIN_URL).execute();
			sessionId = response.cookie("JSESSIONID");
			//Notice, genisis requires cookies to login
			//Specifically the JSESSIONID
			response = Jsoup.connect(LOGIN_URL)
			.userAgent("Mozilla")
			.data("j_password", password)
			.data("j_username", username)
			.method(Method.POST)
			.cookie("JSESSIONID", sessionId)
			.execute();
			
			//New sessionId is given when there is a login attempt
			sessionId = response.cookie("JSESSIONID");
			
		if(response.body().contains("Invalid user name or password"))
			return "Invalid username or password";
			
			studentResponse = Jsoup.connect("https://parents.edison.k12.nj.us/genesis/parents?module=home&studentid=2019915&action=form")
					.method(Method.GET)
					.cookie("JSESSIONID", sessionId)
					.execute();
			
			//GradebookResponse
			
			parseStudentResponse();
			return null;
		}
		catch(IOException e){
			e.printStackTrace();
			return "Unspecified Connection Error";
		}
		
	}
	
	private Student[] parseStudentResponse() throws IOException{
		Document doc = Jsoup.parse(studentResponse.body());
		Elements notecards = doc.getElementsByClass("notecard");
		
		Student[] students = new Student[notecards.size()];
		
		for(int i = 0; i < students.length; i++){
			String age = notecards.get(i).getElementsByClass("listroweven").get(3)
					.getAllElements().get(2).text();
			String birthday = notecards.get(i).getElementsByClass("listroweven").get(4)
					.getAllElements().get(2).text();
			String homeroom = notecards.get(i).getElementsByClass("listroweven").get(1)
					.getAllElements().get(0).text();
			String counselor = notecards.get(i).getElementsByClass("listroweven").get(2)
					.getAllElements().get(0).text();
			String name = notecards.get(i).select("td[valign=top]").get(1).select("td[style=font-size: 1.5em;]")
					.first().text();
			String grade = notecards.get(i).select("td[valign=top]").get(1).select("td[style=text-align: center;]")
					.first().text();
			String header = notecards.get(i).select("td[valign=top]").get(1).select("td[style=font-size:.8em; white-space: nowrap; text-transform: uppercase]")
					.first().text();
			String[] split = header.split("\\|");
			String school = split[0].replaceAll("\u00A0", "").trim();
			String schoolId = split[1].replaceAll("\u00A0", "").trim().replace("Student ID: ", "");
			String stateId = split[2].replaceAll("\u00A0", "").trim().replace("State ID: ", "");
			students[i] = new Student(age, name, grade, birthday, homeroom, counselor,
					schoolId, school, stateId);
			students[i].parseGradeBook();
			
			/*System.out.println(homeroom);
			System.out.println(counselor);
			System.out.println(name);
			System.out.println(school);
			System.out.println(schoolId);
			System.out.println(stateId);

			System.out.println(grade);
			//System.out.println(notecards.get(i).select("td[valign=top]").get(1));
			System.out.println(age);
			System.out.println(birthday);
			//students[i] = new Student();
*/		}
		
		return students;
	}

/*	private GradeBook parseGradebookResponse(){
		Document doc = Jsoup.parse(gradeBookResponse.body());
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
		
		//getAssessmenets("asda", "asda", "asdas");
		
		return null;
	}*/
	
	//date in MM/DD/YYYY
	private AssessmentInterface[] getAssessmenets(String course, String date, String markingPeriod, String studentID, Response response){
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
	
}
