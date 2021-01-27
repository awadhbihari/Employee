package com.paypal.bfs.test.employeeserv.utils;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

@Component
public class InputValidator {

    private static final String REQUIRED_ERROR_MESSAGE = "This field is required";

    public List<Errors> validateEmployeeRequest(Employee employeeRequest) {
        List<Errors> errorsList = new ArrayList<>();

        checkForRequired(employeeRequest, errorsList);

        return errorsList;

    }

    private void checkForRequired(Employee employeeRequest, List<Errors> errorsList) {
        if(isEmpty(employeeRequest.getFirstName())){
            errorsList.add(new Errors.Builder().setField("First Name").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }

        if(isEmpty(employeeRequest.getLastName())){
            errorsList.add(new Errors.Builder().setField("Last Name").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }

        if(isEmpty(employeeRequest.getDateOfBirth())){
            errorsList.add(new Errors.Builder().setField("Date of Birth").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }

        if(isEmpty(employeeRequest.getAddress().getLine1())){
            errorsList.add(new Errors.Builder().setField("Address Line 1").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }

        if(isEmpty(employeeRequest.getAddress().getCity())){
            errorsList.add(new Errors.Builder().setField("city").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }
        
        if(isEmpty(employeeRequest.getAddress().getState())){
            errorsList.add(new Errors.Builder().setField("state").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }
        
        if(isEmpty(employeeRequest.getAddress().getCountry())){
        	errorsList.add(new Errors.Builder().setField("country").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }
        
        if(isEmpty(employeeRequest.getAddress().getZipCode())){
            errorsList.add(new Errors.Builder().setField("zip code").setMessage(REQUIRED_ERROR_MESSAGE).build());
        }
    }

	private boolean isEmpty(String strValue) {
		return strValue == null || strValue.trim().isEmpty();
	}
}
