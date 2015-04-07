package io.github.mimerme.parentportal;

public class Class {
	Course course;
	Teacher teacher;
	
	public Class(Course course, Teacher teacher){
		this.course = course;
		this.teacher = teacher;
	}
	
	public Course getCourse(){
		return course;
	}
	public Teacher getTeacher(){
		return teacher;
	}
	
	public String toString(){
		return course.courseName +  " : " + course.average + " : " + course.asessments
				+ " : " + teacher.name + " : " + teacher.email;
	}
	
	
}
