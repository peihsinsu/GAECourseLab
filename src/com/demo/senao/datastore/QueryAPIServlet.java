package com.demo.senao.datastore;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
public class QueryAPIServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService datastore =
				DatastoreServiceFactory.getDatastoreService();


		Query query = new Query("Employee");
		
		Query.Filter nameFilter = new Query.FilterPredicate(
		              "name", FilterOperator.EQUAL, "simonsu@mitac.com.tw");
		
		query.setFilter(nameFilter); 
		
		PreparedQuery results = datastore.prepare(query);
		Iterator iter = results.asIterable().iterator();
		while(iter.hasNext()) {
			Entity ent = (Entity) iter.next();
			out.println(ent);
		}


	}
}
