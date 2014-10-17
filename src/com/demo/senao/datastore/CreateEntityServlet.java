package com.demo.senao.datastore;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class CreateEntityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService datastore =
		DatastoreServiceFactory.getDatastoreService();

		Entity employee = new Entity("Employee");
		employee.setProperty("name", "simonsu@mitac.com.tw");
		employee.setProperty("hireDate", new Date());
		Key empKey = datastore.put(employee);

		out.println(empKey.getId()); //Will be use by GetEntity
	}
}
