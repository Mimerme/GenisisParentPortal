package io.github.mimerme.interfaces;

public interface TeacherInterface {
	String name = "Mr.X";
	String email = "xxxxx@xxxxx.com";
	
	default String getName(){return name;}
	default String getEmail(){return email;}
}
