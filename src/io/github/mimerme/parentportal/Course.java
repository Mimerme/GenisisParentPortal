package io.github.mimerme.parentportal;

import java.util.ArrayList;

import io.github.mimerme.interfaces.AssessmentInterface;
import io.github.mimerme.interfaces.CourseInterface;

public class Course implements CourseInterface{
	String courseName;
	String average;
	ArrayList<Assessment> asessments = new ArrayList<Assessment>();
	
	public Course(String name, String average, Assessment[] assesments){
		this.courseName = name;
		this.average = average;
		this.asessments = asessments;
	}
	public void addAssessment(Assessment assessment){
		asessments.add(assessment);
	}
}
