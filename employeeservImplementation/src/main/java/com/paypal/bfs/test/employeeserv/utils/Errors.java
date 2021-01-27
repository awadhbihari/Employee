package com.paypal.bfs.test.employeeserv.utils;

public class Errors {

    private String field;
    private String message;
    
	public Errors(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Errors [field=" + field + ", message=" + message + "]";
	}
	
	public static class Builder{
		
	    private String field;
	    private String message;
	    
		public Builder setField(String field) {
			this.field = field;
			return this;
		}
		
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		
		public Errors build() {
			Errors errors = new Errors(field, message);

			return errors;
		}
	}
	
}
