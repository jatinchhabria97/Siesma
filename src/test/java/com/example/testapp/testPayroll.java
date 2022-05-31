package com.example.testapp;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testPayroll {
	@Autowired
	Payroll payroll;
	
	
	@Test
	@Order(1)
	public void testCase() {
		List<Employee> list = new ArrayList<Employee>();
		Employee emp = new Employee();
		emp.setAnnualSalary(60050);
		emp.setFirstName("Johny");
		emp.setLastName("Depp");
		emp.setPaymentMonth(03);
		emp.setSuperRate(5.5);
		list.add(emp);
		payroll.send(list);
	}
}
