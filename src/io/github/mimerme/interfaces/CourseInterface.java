package io.github.mimerme.interfaces;

import java.util.ArrayList;

public interface CourseInterface {
	String CourseName = "";
	ArrayList<AssessmentInterface> assessments = new ArrayList<AssessmentInterface>();
	String average = "";
	
	default public ArrayList<AssessmentInterface> getAssessments() {
		return assessments;
	}
	
	default public String getCourseName() {
		return CourseName;
	}
	
	default public String getAverage() {
		return average;
	}
}
