package com.paypal.bfs.test.employeeserv.impl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.utils.Errors;
import com.paypal.bfs.test.employeeserv.utils.InputValidator;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	private EmployeeService service;
	
    @Autowired
    private InputValidator inputValidator;

	@Override
	public ResponseEntity<Employee> employeeGetById(String id) {

		//        Employee employee = new Employee();
		//        employee.setId(Integer.valueOf(id));
		//        employee.setFirstName("BFS");
		//        employee.setLastName("Developer");
		Integer employeeId;
		try {
			employeeId = Integer.valueOf(id);
		} catch(NumberFormatException nef) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Employee employee = service.getEmployeeById(employeeId);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createEmployee(String idempotencyKey, Employee employee) {
		
        List<Errors> error = inputValidator.validateEmployeeRequest(employee);
        if(error.size() > 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
		EmployeeEntity savedEmployee = service.save(employee, idempotencyKey);
		if(savedEmployee != null) {
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedEmployee.getId()).toUri();
			return ResponseEntity.created(location).build();
		} else 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create employee");

	}
}