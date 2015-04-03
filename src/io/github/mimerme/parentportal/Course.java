package io.github.mimerme.parentportal;

import java.util.ArrayList;

import io.github.mimerme.interfaces.AssessmentInterface;
import io.github.mimerme.interfaces.CourseInterface;

public class Course implements CourseInterface{
	String courseName;
	String average;
	AssessmentInterface[] asessments;
	
	public Course(String name, String average, AssessmentInterface[] assesments){
		this.courseName = name;
		this.average = average;
		this.asessments = asessments;
	}
}
