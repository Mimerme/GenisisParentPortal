package io.github.mimerme.parentportal;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Student {
	private GradeBook gradeBook;
	private String age;
	private String birthday;
	private String homeroom;
	private String counselor;
	private String studentID;
	private String stateID;
	private String school;
	private String name;
	private String grade;
	
	public Student(String age, String name, String grade, String birthday, String homeroom, String counselor,
			String studentID, String school, String stateID){
		this.age = age;
		this.name = name;
		this.birthday = birthday;
		this.homeroom = homeroom;
		this.counselor = counselor;
		this.studentID = studentID;
		this.school = school;
		this.grade = grade;
		this.stateID = stateID;
	}
	
	public void parseGradeBook(String markingPeriod, String date, String course) throws IOException{
		Response gradeBookResponse = Jsoup.connect("https://parents.edison.k12.nj.us/genesis/parents?module=gradebook&"
				+ "studentid=" + studentID
				+ "&action=form")
				.cookie("JSESSIONID", Portal.sessionId)
				.execute();
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
			//Get the basic class info
			classes[i] = new Class(new Course(element.getElementsByTag("u").text(), element.getElementsByClass("cellRight")
					.first()
					.getElementsByClass("cellRight")
					.first().text(), null),
					new Teacher(element.getElementsByClass("cellLeft").get(1).text().replace("Email:", "")
							, element.getElementsByClass("cellLeft").get(1).getElementsByTag("a").attr("href").replace("mailto:", "")));
			//Extract the course and section
			//Connect for the list of assessments
			//Parse the list
			
			System.out.println(classes[i]);
		}
		
		//getAssessmenets("asda", "asda", "asdas");
		
		return;
	
	}
	
}
