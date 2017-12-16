package com.generic.fnd.entity;

import com.generic.fnd.BasicEntity;

public class EmployeeEntity implements BasicEntity{
	
	private String firstName;
	private String lastName;	

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

	@Override
	public String dbConfigFileName() {
		// TODO Auto-generated method stub
		return "Employee";
		
	}

}
