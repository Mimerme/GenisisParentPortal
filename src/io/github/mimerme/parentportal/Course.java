package io.github.mimerme.parentportal;

import java.util.ArrayList;

import io.github.mimerme.interfaces.AssessmentInterface;
import io.github.mimerme.interfaces.CourseInterface;

public class Course implements CourseInterface{
	String courseName;
	String average;
	
	ArrayList<AssessmentInterface> assessments = new ArrayList<AssessmentInterface>();
	
	public Course(String name, String average){
		this.courseName = name;
		this.average = average;
	}
}
