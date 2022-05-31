package com.example.testapp;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.testapp.exception.ApiCustomException;
import com.example.testapp.exception.CustomException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Validated
@RestController
public class EmployeeController {

	public List<Payroll> list = new ArrayList<Payroll>();
	public List<Employee> list1 = new ArrayList<Employee>();
	public List<Payroll> out = new ArrayList<Payroll>();
	public List<Employee> serve1 = new ArrayList<Employee>();
	
	@PostMapping("/employee")
	public List input(@Validated @RequestBody List<@Valid Employee> sampleEmployee){
			List<Object> sections1 = new ArrayList <Object> ();
			List<Employee> copy = sampleEmployee;
		    list = Payroll.send(sampleEmployee);
		    serve1 = sampleEmployee;
		    out = list;
		    
			for(int i = 0; i<list.size();i++)
			 {
				 sections1.add(copy.get(i));
				 sections1.add(out.get(i));
			 }
			return sections1;
	}
	
	@PostMapping(path = "/employee/upload")
	public List input(@Validated @RequestBody MultipartFile file) throws IOException{
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		List<Employee> serve = new ArrayList<Employee>();
		List<String> sending = new ArrayList<String>();
		List<Object> sections = new ArrayList <Object> ();
		
		if(ext.equalsIgnoreCase("csv")){
//		 try {
			 InputStreamReader reader = new InputStreamReader(file.getInputStream());
			 CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
			 List<String[]> rows = csvReader.readAll();
			 for(String[] row: rows) {
				 for(String cell : row) {
					 sending.add(cell);
				 }
			 }
			 if(sending.size()%5 == 0) {
				 System.out.println("Successfully uploaded file");
			 }
			 else {
				 throw new ApiCustomException("Invalid number or Arguments in the FIle");
			 }
			 int counter = 0; 
			 for(int i =0; i<sending.size()/5;i++) {
				 Employee csvEmp = new Employee();
				 for(int j=0; j<5;j++){
					 if(j==0) {
						 if(sending.get(counter) instanceof String) {
							 csvEmp.setFirstName(sending.get(counter));
						 }
						 else {
							 csvEmp.setFirstName("DUMMY(UNABLE TO PROCESS) -  USING DEFAULT");
						 }
					 }
					 else if(j==1)
					 {
						 if(sending.get(counter) instanceof String) {
							 csvEmp.setLastName(sending.get(counter));
						 }
						 else {
							 csvEmp.setLastName("DUMMY(UNABLE TO PROCESS)  - USING DEFAULT");
						 }
					 }
					 else if(j==2)
					 {
						 try{
							 int num = Integer.parseInt(sending.get(counter));
							 csvEmp.setAnnualSalary(num);
						 }
						 catch (Exception e) {
							 throw new ApiCustomException("Annual salary of No."+" "+i+1+" "+" Element is not an int");
						 }
					 }
					 else if(j==3)
					 {
						 try{
							 int num = Integer.parseInt(sending.get(counter));
							 csvEmp.setPaymentMonth(num);
						 }
						 catch (Exception e) {
							 throw new ApiCustomException("payment month of No."+" "+i+1+" "+"th Element is not an int");
						 }
					 }
					 else if(j==4)
					 {
						 try{
							 double num = Double.parseDouble(sending.get(counter));
							 csvEmp.setSuperRate(num);
						 }
						 catch (Exception e) {
							 throw new ApiCustomException("Super Rate of No"+" "+i+1+" "+"th Element is not a Double");
						 }
					 }
					 
					 counter++;
				 }
//				 System.out.println(csvEmp.getFirstName()+"EMP");
//				 System.out.println(csvEmp.getLastName()+"EMP");
//				 System.out.println(csvEmp.getAnnualSalary()+"EMP");
//				 System.out.println(csvEmp.getPaymentMonth()+"EMP");
//				 System.out.println(csvEmp.getSuperRate()+"EMP");
				 serve.add(csvEmp);
				
			out = Payroll.send(serve);
				 
			 }
			 
			 for(int i = 0; i<out.size();i++)
			 {
				 sections.add(serve.get(i));
				 sections.add(out.get(i));
			 }
			 serve1 = serve;
			
			 return sections;
		}
//		 catch(Exception E) {
//			 throw new ApiCustomException("THE VALUES ARE NOT IN CORRECT VALID ACCEPTED FORMAT, CHANGE THE VALUES AND TRY AGAIN");
//		 }
//		}
		else {
			throw new ApiCustomException("THE file is not a csv file");
		}
	}
	@GetMapping("/employee/get")
	public void exportToCsv(HttpServletResponse response) throws IOException{
		
		 response.setContentType("text/csv");
		
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String currentDateTime = dateFormatter.format(new Date());
	     
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
	        response.setHeader(headerKey, headerValue);
	 
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	        String[] csvHeader = {"1stName & PayDay", "LastName & PayDay", "GrossInc", "tax", "Super","Net"};
	        String[] nameMapping = {"fromDate", "toDate", "grossIncome", "tax", "superAmt","netIncome"};
	        String[] nameMapping1 = {"firstName", "lastName", "annualSalary", "paymentMonth", "superRate"};
	         
	        csvWriter.writeHeader(csvHeader);
	         
	        for (Employee user : serve1) {
	            csvWriter.write(user, nameMapping1);
	            for (Payroll user1 : out) {
		            csvWriter.write(user1, nameMapping);
		            break;
		        }
	        }
	        csvWriter.close();  
	    }
		
	}
	

