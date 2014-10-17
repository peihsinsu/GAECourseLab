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
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
public class UsingTransactionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Transaction txn = datastore.beginTransaction();
		try {
			Key ekey = KeyFactory.createKey("Employee", "Joe");
			Entity employee;
			employee = datastore.get(ekey);
			
			/* ... reading and writing on employee ... */

			datastore.put(employee);
			txn.commit();
			out.println("Transaction done...");
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			out.println("Delete entity error, e:" + e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

	}
}
