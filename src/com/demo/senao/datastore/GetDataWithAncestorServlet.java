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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class GetDataWithAncestorServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Jobs");
		Key ancestor = KeyFactory.createKey("User", "simonsu@mitac.com.tw");
		q.setAncestor(ancestor);

		PreparedQuery results = ds.prepare(q);
		Iterator iter = results.asIterable().iterator();
		while(iter.hasNext()) {
			Entity ent = (Entity) iter.next();
			out.println(ent);
		}

		
	}
}
