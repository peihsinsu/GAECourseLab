package com.demo.senao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class TestWebServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity job = new Entity("User", "simonsu@mitac.com.tw");
		job.setProperty("name", "Simon Su");
		job.setProperty("start", "20140103");
		job.setProperty("create", new Date());
		try {
		Key k = ds.put(job);
			out.println("Done..." + k.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//		Key userKey = KeyFactory.createKey("User", "simonsu@mitac.com.tw");
//		Entity job = new Entity("Jobs", userKey);
//		job.setProperty("name", "engineer");
//		job.setProperty("start", new Date());
//		try {
//			Key k = ds.put(job);
//			out.println("Done..." + k.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//		Key userKey = KeyFactory.createKey("User", "simonsu@mitac.com.tw");
//		Entity job = new Entity("Jobs", userKey);
//		job.setProperty("name", "engineer");
//		job.setProperty("start", new Date());
//		try {
//			Key k = ds.put(job);
//			out.println("Done..." + k.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
}
