package com.demo.senao.memcache;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class MemcacheServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		cache.setErrorHandler(
		    ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		// read from cache
		String key = "name";
		String value = "simonsu @ " + new Date().toString();  

		// save to cache
		if (value == null) {
		  cache.put(key, value);
		  out.println("Save data to cache done");
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		cache.setErrorHandler(
		    ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		// read from cache
		String key = "name";
		byte[] value = (byte[]) cache.get(key); 

		out.println("Get data from cache: " + key + "=" + value);
	}
}
