package io.github.mimerme.parentportal;

import io.github.mimerme.interfaces.AssessmentInterface;

public class Assessment implements AssessmentInterface{
	String name;
	String date;
	String grade;
	
	public Assessment(String name, String date, String grade){
		this.name = name;
		this.date = date;
		this.grade = grade;
	}
}
