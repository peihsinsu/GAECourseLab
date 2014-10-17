package com.demo.senao.datastore;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class GetEntityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService datastore =
				DatastoreServiceFactory.getDatastoreService();

		// Later, use the key to retrieve the entity
		// EntityId need to be retrieve from CreateEntityServlet
		Key userKey = KeyFactory.createKey("Employee", "[EntityId from CreateEntityServlet]");
		try {
			Entity user = datastore.get(userKey);
			user.getProperty("name");
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}


	}
}
