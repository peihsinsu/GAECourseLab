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
public class QueryAPIComposeFilterServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService datastore =
				DatastoreServiceFactory.getDatastoreService();


		Query q = new Query("Employee");
		Query.Filter filter1 = new Query.FilterPredicate("name", FilterOperator.EQUAL, "Simon Su");
		Query.Filter filter2 = new Query.FilterPredicate("name", FilterOperator.EQUAL, "simonsu@mitac.com.tw");
		Query.Filter comboFilter = 
				Query.CompositeFilterOperator.or(filter1, filter2);

		q.setFilter(comboFilter);

		q.addSort("name");

		PreparedQuery results = datastore.prepare(q);
		Iterator iter = results.asIterable().iterator();
		while(iter.hasNext()) {
			Entity ent = (Entity) iter.next();
			out.println(ent);
		}
	}
}
