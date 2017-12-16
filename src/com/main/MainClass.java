package com.main;

import java.util.List;

import com.generic.dao.GenericDaoImpl;
import com.generic.exception.GenericException;
import com.generic.fnd.entity.EmployeeEntity;
import com.generic.fnd.entity.StudentEntity;

public class MainClass  {
	
	public static void main(String[] args) throws GenericException {
		GenericDaoImpl dao = new GenericDaoImpl();
	
		EmployeeEntity emp = new EmployeeEntity();
		emp.setFirstName("Varun");
		emp.setLastName("Chopra");
		dao.create(emp);
		
		emp = new EmployeeEntity();		
		emp.setFirstName("Random");
		emp.setLastName("Name");
		dao.create(emp);
		
		StudentEntity student = new StudentEntity();
		student.setFirstName("kid");
		student.setLastName("kid's lastname");
		student.setAge("10");
		student.setRollNumber("287");
		dao.create(student);
		
		
		List<StudentEntity> students = dao.list(new StudentEntity());
		System.out.println(students);
		
		List<EmployeeEntity> emps = dao.list(new EmployeeEntity());
		System.out.println(emps);
		
		
		
	}

}
