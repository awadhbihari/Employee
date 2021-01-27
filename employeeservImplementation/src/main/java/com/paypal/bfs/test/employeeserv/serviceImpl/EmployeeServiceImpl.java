package com.paypal.bfs.test.employeeserv.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeDao;

	@Override
	public EmployeeEntity save(Employee employee, String idempotencyKey) {

		if(employee != null) {
			EmployeeEntity employeeEntity = mapRequestToEntity(employee); 
			employeeEntity.setIdempotency(idempotencyKey);
			try {
				return employeeDao.save(employeeEntity);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private EmployeeEntity mapRequestToEntity(Employee employee) {
		EmployeeEntity employeeEntity = new EmployeeEntity();

		employeeEntity.setFirstName(employee.getFirstName());
		employeeEntity.setLastName(employee.getLastName());
		employeeEntity.setDateOfBirth(employee.getDateOfBirth());
		employeeEntity.setAddress(mapRequestToEntity(employee.getAddress()));
		return employeeEntity;
	}

	private AddressEntity mapRequestToEntity(Address address) {

		AddressEntity addressEntity = new AddressEntity();

		addressEntity.setLine1(address.getLine1());
		addressEntity.setLine2(address.getLine2());
		addressEntity.setCity(address.getCity());
		addressEntity.setState(address.getState());
		addressEntity.setCountry(address.getCountry());
		addressEntity.setZipCode(address.getZipCode());
		return addressEntity;
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		Optional<EmployeeEntity> employeeOptional = employeeDao.findById(id.longValue());

		if(employeeOptional.isPresent())
			return mapEntityToResponse(employeeOptional.get());
		else 
			throw new ResourceNotFoundException("Employee " + id + " not found");
	}

	private Employee mapEntityToResponse(EmployeeEntity employeeEntity) {

		Employee employee = new Employee();

		employee.setId(employeeEntity.getId().intValue());
		employee.setFirstName(employeeEntity.getFirstName());
		employee.setLastName(employeeEntity.getLastName());
		employee.setDateOfBirth(employeeEntity.getDateOfBirth());
		employee.setAddress(mapEntityToResponse(employeeEntity.getAddress()));
		System.out.println("Employye impodency key :" + employeeEntity.getIdempotency());
		return employee;
	}

	private Address mapEntityToResponse(AddressEntity addressEntity) {

		Address address = new Address();

		address.setLine1(addressEntity.getLine1());
		address.setLine2(addressEntity.getLine2());
		address.setCity(addressEntity.getCity());
		address.setState(addressEntity.getState());
		address.setCountry(addressEntity.getCountry());
		address.setZipCode(addressEntity.getZipCode());

		return address;
	}

}
