package hai704i.tp2.demo.service;

import java.util.List;

import javax.jws.WebService;

import hai704i.tp2.demo.exceptions.EmployeeAlreadyExistsException;
import hai704i.tp2.demo.exceptions.EmployeeNotFoundException;
import hai704i.tp2.demo.model.Employee;
import hai704i.tp2.demo.repository.EmployeeRepository;
import hai704i.tp2.demo.repository.EmployeeRepositoryImpl;

@WebService(endpointInterface="hai704i.tp2.demo.service.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	/* ATTRIBUTES */
	private EmployeeRepository repository = new EmployeeRepositoryImpl();

	/* METHODS */
	@Override
	public int count() {
		return repository.count();
	}

	@Override
	public List<Employee> getEmployees() {
		return repository.getEmployees();
	}

	@Override
	public Employee addEmployee(int id, String name) throws EmployeeAlreadyExistsException {
		return repository.addEmployee(id, name);
	}

	@Override
	public Employee getEmployee(int id) throws EmployeeNotFoundException {
		return repository.getEmployee(id);
	}

	@Override
	public Employee updateEmployee(int id, String name) throws EmployeeNotFoundException {
		return repository.updateEmployee(id, name);
	}

	@Override
	public boolean deleteEmployee(int id) throws EmployeeNotFoundException {
		return repository.deleteEmployee(id);
	}
}
