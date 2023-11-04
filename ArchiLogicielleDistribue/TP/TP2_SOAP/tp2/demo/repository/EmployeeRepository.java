package hai704i.tp2.demo.repository;

import java.util.List;

import hai704i.tp2.demo.exceptions.EmployeeAlreadyExistsException;
import hai704i.tp2.demo.exceptions.EmployeeNotFoundException;
import hai704i.tp2.demo.model.Employee;

public interface EmployeeRepository {

	/* METHODS */
	int count();
	
	List<Employee> getEmployees();
	
	Employee addEmployee(int id, String name) throws EmployeeAlreadyExistsException;
	
	Employee getEmployee(int id) throws EmployeeNotFoundException;
	
	Employee updateEmployee(int id, String name) throws EmployeeNotFoundException;
	
	boolean deleteEmployee(int id) throws EmployeeNotFoundException;
}
