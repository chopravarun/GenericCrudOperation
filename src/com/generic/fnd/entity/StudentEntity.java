package com.generic.fnd.entity;

import com.generic.fnd.BasicEntity;

public class StudentEntity implements BasicEntity{
	
	private String firstName;
	private String lastName;
	private String age;
	private String rollNumber;
		
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	@Override
	public String dbConfigFileName() {
		// TODO Auto-generated method stub
		return "Student";
	}

}
