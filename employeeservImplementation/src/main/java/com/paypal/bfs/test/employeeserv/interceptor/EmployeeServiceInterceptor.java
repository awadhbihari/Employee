package com.paypal.bfs.test.employeeserv.interceptor;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;

@Component
public class EmployeeServiceInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	EmployeeRepository employeeDao;

	@Override
	public boolean preHandle
	(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		if("POST".equals(request.getMethod())) {
			String idempotencyKey = request.getHeader("idempotency-key");
			if(idempotencyKey != null) {
				List<EmployeeEntity> employeeEntitys = employeeDao.findByIdempotency(idempotencyKey);
				if(employeeEntitys != null && employeeEntitys.size()>0) {

					URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(employeeEntitys.get(0).getId()).toUri();

					response.setStatus(HttpStatus.NOT_MODIFIED.value());
					response.encodeURL(location.getPath());
					return false;
				}
			} else {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				return false;
			}
		}
		return true;
	}
}