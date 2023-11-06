package hai704i.tp2.demo.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import hai704i.tp2.demo.exceptions.EmployeeAlreadyExistsException;
import hai704i.tp2.demo.exceptions.EmployeeNotFoundException;
import hai704i.tp2.demo.model.Employee;

@WebService
public interface EmployeeService {
	
	/* METHODS */
	@WebMethod
	int count();
	
	@WebMethod
	List<Employee> getEmployees();
	
	@WebMethod
	Employee addEmployee(int id, String name) throws EmployeeAlreadyExistsException;
	
	@WebMethod
	Employee getEmployee(int id) throws EmployeeNotFoundException;
	
	@WebMethod
	Employee updateEmployee(int id, String name) throws EmployeeNotFoundException;
	
	@WebMethod
	boolean deleteEmployee(int id) throws EmployeeNotFoundException;
}
