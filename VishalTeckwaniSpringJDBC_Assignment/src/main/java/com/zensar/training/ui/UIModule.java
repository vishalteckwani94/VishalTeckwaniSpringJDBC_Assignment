package com.zensar.training.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zensar.training.bean.Employee;
import com.zensar.training.bean.Gender;
import com.zensar.training.service.EmployeeService;
import com.zensar.training.service.EmployeeServiceImpl;

public class UIModule {
	
	private static DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;
	
	@Qualifier("datasource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	private static void blankLines(int num) {
		for(int i=1;i<=num; i++)
			System.out.println();
	}
	public static void addInfo() {
		blankLines(3);
		InputPrompter prompter= new InputPrompter();
		
		String name= prompter.promptForStringInput("Enter Name");
		char grade=prompter.promptForCharInput("Enter Grade[A,B,C]");
		LocalDate hiredDate=prompter.promptForDateInput("Enter DOJ", "dd-MMM-yyyy");
		double salary=prompter.promptForDoubleInput("Enter Basic Salary");
		Gender gender=prompter.promptForGenderInput("Enter Gender [1.MALE 2.FEMALE]");
		//Id we are not taking as parameter
		Employee employee=new Employee();
		employee.setName(name);
		employee.setGrade(grade);
		employee.setHiredDate(hiredDate);
		employee.setBasicSalary(salary);
		employee.setGender(gender);	
		EmployeeService employeeService=new EmployeeServiceImpl();
		try {
			boolean result=employeeService.addEmployee(jdbcTemplate, employee);
			if(result==true)
				System.out.println("\t\t\tAdded Successfully");
			else
				System.out.println("\t\t\tNot Added ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateInfo() {
		blankLines(3);
		InputPrompter prompter= new InputPrompter();
		EmployeeService employeeService=new EmployeeServiceImpl();
		
		int editableID=prompter.promptForIntInput("Enter ID to Update");
		
		try {
			Employee employee=employeeService.findEmployee(jdbcTemplate, editableID);
			if(employee==null)
			{
				System.out.println("\t\t Not Found....Try Diffrent Id \n----------------------------------------------------------------------------------------------------");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String name= prompter.promptForStringInput("Enter Name");
		char grade=prompter.promptForCharInput("Enter Grade [A,B,C]");
		LocalDate hiredDate=prompter.promptForDateInput("Enter DOJ", "dd-MMM-yyyy");
		double salary=prompter.promptForDoubleInput("Enter Basic Salary");
		Gender gender=prompter.promptForGenderInput("Enter Gender [1.MALE 2.FEMALE]");	
		// more code here
		Employee employee=new Employee();
		employee.setId(editableID);
		employee.setName(name);
		employee.setGrade(grade);
		employee.setHiredDate(hiredDate);
		employee.setBasicSalary(salary);
		employee.setGender(gender);	
		
		try {
			boolean result=employeeService.updateEmployee(jdbcTemplate, employee);
			if(result==true)
				System.out.println("\t\t\tUpdated Successfully\n------------------------------------------------------------------------------------------\n");
			else
				System.out.println("\t\t\tNot Updated ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}	
	public static void searchInfo() {
		blankLines(3);
		InputPrompter prompter= new InputPrompter();
		int searchId=prompter.promptForIntInput("Enter ID to Search");
		System.out.println("--------------------------------------------------------------------------");
		Consumer<Employee> consumer=(e)->{
			System.out.println("\t\t Id: "+e.getId());
			System.out.println("\t\t Name: "+e.getName());
			System.out.println("\t\t DOJ: "+e.getHiredDate());
			System.out.println("\t\t Salary: "+e.getBasicSalary());
			System.out.println("\t\t Grade: "+e.getGrade());
			System.out.println("\t\t Gender: "+e.getGender());
			System.out.println("--------------------------------------------------------------------------\n");
		};
		EmployeeService emService=new EmployeeServiceImpl();
		try {
			Employee employee=emService.findEmployee(jdbcTemplate, searchId);
			if(employee==null)
			{
				System.out.println("\t\t Not Found....Try Diffrent Id");
				return ;
			}
			consumer.accept(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static  void listInfo() {
		blankLines(3);
		
		Consumer<Employee> consumer=(e)->{
			System.out.println("\n-----------------------------------------------------------------------------");
			System.out.printf("%-5d",e.getId());//print in formated way %-5d-->left align of five integer
			System.out.printf("%-25s",e.getName());
			System.out.printf("%-15s",e.getHiredDate().toString());
			System.out.printf("%-15.2f",e.getBasicSalary());//float
			System.out.printf("%-5s",e.getGrade()+"");
			System.out.printf("%-10s",e.getGender()+"");
			System.out.println();
		};

		List<Employee> employees=null;
		EmployeeService empService=new EmployeeServiceImpl();
		try {
			employees=empService.findAllEmployee(jdbcTemplate);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		employees.stream().forEach(consumer);//for each is calling the consumer accept method		
	}
	
	public static void deleteInfo() {
		blankLines(3);
		InputPrompter prompter= new InputPrompter();
		int searchId=prompter.promptForIntInput("Enter ID to Delete");
		
		EmployeeService emService=new EmployeeServiceImpl();
		boolean result=false;
		try {
			result = emService.deleteEmployee(jdbcTemplate, new Employee(searchId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result==true)
			System.out.println("\t\t Deleted Successfully");
		else
			System.out.println("\t\t Not Deleted");
		
	}
}
