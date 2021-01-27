package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;

public interface EmployeeService {

	EmployeeEntity save(Employee employee, String idempotencyKey);

	Employee getEmployeeById(Integer id);

}
