package com.example.testapp;
import java.util.ArrayList;
import java.time.Month;
import java.util.List;
import java.time.YearMonth;

import java.text.DateFormatSymbols;

import org.springframework.stereotype.Service;

import com.example.testapp.exception.ApiCustomException;
import com.example.testapp.exception.CustomException;

@Service
public class Payroll {
	
	private String fromDate;
	private String toDate;
	private int grossIncome;
	private long tax;
	private long superAmt;
	private long netIncome;
	
	public Payroll() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Payroll(String fromDate, String toDate, int grossIncome, long tax, long superAmt, long netIncome) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.grossIncome = grossIncome;
		this.tax = tax;
		this.superAmt = superAmt;
		this.netIncome = netIncome;
	}
	
	
	
	public String getFromDate() {
		return fromDate;
	}



	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}



	public String getToDate() {
		return toDate;
	}



	public void setToDate(String toDate) {
		this.toDate = toDate;
	}



	public int getGrossIncome() {
		return grossIncome;
	}



	public void setGrossIncome(int grossIncome) {
		this.grossIncome = grossIncome;
	}



	public long getTax() {
		return tax;
	}



	public void setTax(long tax) {
		this.tax = tax;
	}



	public long getSuperAmt() {
		return superAmt;
	}



	public void setSuperAmt(long l) {
		this.superAmt = l;
	}



	public long getNetIncome() {
		return netIncome;
	}



	public void setNetIncome(long netIncome) {
		this.netIncome = netIncome;
	}



	public static List<Payroll> send (List<Employee> input){
		List<Payroll> payment = new ArrayList<Payroll>();

		for(int i=0; i<input.size();i++) {
			Payroll sample = new Payroll();
			try {
				int days = getNumberOfDaysInMonth(2022, input.get(i).getPaymentMonth()+1);
				sample.setFromDate("01"+" "+new DateFormatSymbols().getMonths()[input.get(i).getPaymentMonth()]);
				sample.setToDate(days+" "+new DateFormatSymbols().getMonths()[input.get(i).getPaymentMonth()]);
			}
			catch(Exception E) {
				throw new ApiCustomException("A month value entered is irrelavant: IT MUST BE BETWEEN 0 and 11");
			}
			int range = input.get(i).getAnnualSalary();
			int temp = Math.round(range/12);
			sample.setGrossIncome(temp);
		    long tax = 0;
		    if(range >= 0 && range <= 18200) {
		    	sample.setTax(0);
		    	tax = 0;
		    }
		    else if(range >= 18201 && range <= 37000){
		    	tax = (long)Math.round(((range-18200)*0.19)/12);
		    	sample.setTax(tax);
		    }
		    else if(range >= 37001 && range <= 87000){
		    	tax =(long)Math.round((3572+(range - 37000)*0.325)/12);		    	
		    	sample.setTax(tax);
		    }
		    else if(range >= 87001 && range <= 180000){
		    	tax =(long)Math.round((19822+(range - 87000)*0.37)/12);		    	
		    	sample.setTax(tax);
		    }
		    else if(range >= 180001){
		    	tax =(long)Math.round((54232+(range -180000)*0.45)/12);		    	
		    	sample.setTax(tax);
		    }
		   if(input.get(i).getSuperRate()< 50.0)
		    sample.setSuperAmt(Math.round((temp)*input.get(i).getSuperRate()));
		   else
			   throw new ApiCustomException("A Value in the Enterred data has Above than permisssable super rate");
		    sample.setNetIncome(temp-tax);
		    
		    payment.add(sample);
		}
		return payment;
}
	
	public static int getNumberOfDaysInMonth(int year,int month)
    {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return daysInMonth;
    }



	
}
