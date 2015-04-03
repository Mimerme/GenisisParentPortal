package io.github.mimerme.interfaces;

public interface AssessmentInterface {
	String name = "test";
	String date = "1/1/2015";
	String grade = "00";
	
	default String getName(){return name;}
	default String getDate(){return date;}
	default String getGrade(){return grade;}

}
