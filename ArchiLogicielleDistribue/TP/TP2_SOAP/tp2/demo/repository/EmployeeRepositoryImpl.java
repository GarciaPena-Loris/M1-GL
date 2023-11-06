package hai704i.tp2.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import hai704i.tp2.demo.exceptions.EmployeeAlreadyExistsException;
import hai704i.tp2.demo.exceptions.EmployeeNotFoundException;
import hai704i.tp2.demo.model.Employee;

public class EmployeeRepositoryImpl implements EmployeeRepository {
	
	/* ATTRIBUTES */
	private List<Employee> employees;
	
	/* CONSTRUCTORS */
	public EmployeeRepositoryImpl() {
		employees = new ArrayList<>();
		employees.addAll(Arrays.asList(
				new Employee(1, "Joe"),
				new Employee(2, "Jane"),
				new Employee(3, "Steve"),
				new Employee(4, "Alice"),
				new Employee(5, "Bob"),
				new Employee(6, "Alicia"),
				new Employee(7, "Tricia"),
				new Employee(8, "Paul"),
				new Employee(9, "Kevin"),
				new Employee(10, "Julia")
		));
	}
	
	/* METHODS */
	@Override
	public int count() {
		return employees.size();
	}

	@Override
	public List<Employee> getEmployees() {
		return employees;
	}

	@Override
	public Employee addEmployee(int id, String name) throws EmployeeAlreadyExistsException {
		
		Optional<Employee> target = employees.stream()
				.filter(e -> e.getId() == id)
				.findFirst();
		
		if (!target.isEmpty())
			throw new EmployeeAlreadyExistsException(
					"Error: An employee with ID "+id+" already exists");
		
		Employee employee = new Employee(id, name);
		employees.add(employee);
		return employee;
	}

	@Override
	public Employee getEmployee(int id) throws EmployeeNotFoundException {
		Optional<Employee> target = employees.stream()
				.filter(e -> e.getId() == id)
				.findFirst();
		
		if (target.isEmpty())
			throw new EmployeeNotFoundException(
					"Error: No employee with ID "+id+" exists");
		
		return target.get();
	}

	@Override
	public Employee updateEmployee(int id, String name) throws EmployeeNotFoundException {
		Optional<Employee> target = employees.stream()
				.filter(e -> e.getId() == id)
				.findFirst();
		
		if (target.isEmpty())
			throw new EmployeeNotFoundException(
					"Error: No employee with ID "+id+" exists");
		
		target.get().setName(name);
		return target.get();
	}

	@Override
	public boolean deleteEmployee(int id) throws EmployeeNotFoundException {
		Optional<Employee> target = employees.stream()
				.filter(e -> e.getId() == id)
				.findFirst();
		
		if (target.isEmpty())
			throw new EmployeeNotFoundException(
					"Error: No employee with ID "+id+" exists");
		
		return employees.remove(target.get());
	}
}
