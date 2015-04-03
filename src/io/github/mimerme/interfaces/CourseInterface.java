package io.github.mimerme.interfaces;

import java.util.ArrayList;

public interface CourseInterface {
	String CourseName = "";
	AssessmentInterface[] assessments = new AssessmentInterface[1];
	String average = "";
	
	default public AssessmentInterface[] getAssessments() {
		return assessments;
	}
	
	default public String getCourseName() {
		return CourseName;
	}
	
	default public String getAverage() {
		return average;
	}
}
