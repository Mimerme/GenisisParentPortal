package io.github.mimerme.parentportal;

public class GradeBook {
	Class[] classes;
	
	public GradeBook(Class[] classes){
		this.classes = classes;
	}
	
	public Class getClass(int index){
		return classes[index];
	}
	
	public Class[] getClasses(){
		return classes;
	}
	
}
