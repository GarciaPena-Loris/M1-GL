package hai704i.tp2.demo.server;

import javax.xml.ws.Endpoint;

import hai704i.tp2.demo.service.EmployeeServiceImpl;

public class EmployeeServicePublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/employeeservice", 
				new EmployeeServiceImpl());
		System.err.println("Server ready");
	}
}
