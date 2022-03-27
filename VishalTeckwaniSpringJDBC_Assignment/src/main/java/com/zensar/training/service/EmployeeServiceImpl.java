package com.zensar.training.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zensar.training.bean.Employee;
import com.zensar.training.bean.Gender;
import com.zensar.training.db.DateConversion;
import com.zensar.training.db.EmployeeDAO;
import com.zensar.training.db.EmployeeSpringJDBCImpl;


public class EmployeeServiceImpl implements EmployeeService {

	EmployeeDAO dao=new EmployeeSpringJDBCImpl(); 
		
	@Override
	public boolean addEmployee(JdbcTemplate jdbcTemplate,Employee employee) throws Exception {
		return dao.addEmployee(jdbcTemplate, employee);
	}

	@Override
	public boolean updateEmployee(JdbcTemplate jdbcTemplate, Employee employee) throws Exception {
		return dao.updateEmployee(jdbcTemplate, employee);
	}


	@Override
	public boolean deleteEmployee(JdbcTemplate jdbcTemplate, Employee employee) throws Exception {
		return dao.deleteEmployee(jdbcTemplate, employee);
	}


	@Override
	public Employee findEmployee(JdbcTemplate jdbcTemplate, int id) throws Exception {
		return dao.findEmployee(jdbcTemplate, id);
	}


	@Override
	public List<Employee> findAllEmployee(JdbcTemplate jdbcTemplate) throws Exception {
		return dao.findAllEmployees(jdbcTemplate);
	}
	
	private class EmployeeRowMapper implements RowMapper<Employee>
	{
		
		public Employee mapRow(ResultSet rs,int rowNum) throws SQLException {
			Employee employee=new Employee();
			employee.setId(rs.getInt(1));
			employee.setName(rs.getString(2));

			java.sql.Date dojdate = rs.getDate(3);
			LocalDate doj = DateConversion.convertToLocalDate(dojdate);
			employee.setHiredDate(doj);
			employee.setGrade(rs.getString(4).charAt(0));
			employee.setBasicSalary(rs.getDouble(5));
			
			char gend = rs.getString(6).charAt(0);
			if (gend == 'M')
				employee.setGender(Gender.MALE);
			else
				employee.setGender(Gender.FEMALE);

			return employee;
		}
	}
}
	
	
