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
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class CreateEntityWithAncestorServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key userKey = KeyFactory.createKey("User", "simonsu@mitac.com.tw");
		Entity job = new Entity("Jobs", userKey);
		job.setProperty("name", "engineer");
		job.setProperty("start", new Date());
		try {
			Key k = ds.put(job);
			out.println("Done..." + k.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
