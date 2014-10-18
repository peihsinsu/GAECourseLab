package com.demo.senao.memcache;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class AsyncMemcacheServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		AsyncMemcacheService cache = MemcacheServiceFactory.getAsyncMemcacheService();
		cache.setErrorHandler(
		    ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		// read from cache
		String key = "name";
		String value = "simonsu @ " + new Date().toString();  

		// save to cache
		if (value == null) {
		  cache.put(key, value);
		  out.println("Save data to async cache done");
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		AsyncMemcacheService cache = MemcacheServiceFactory.getAsyncMemcacheService();
		cache.setErrorHandler(
		    ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		// read from cache
		String key = "name";
		Future<Object> futureValue = cache.get(key); 

		//byte[] value = (byte[]) cache.get(key); 

		out.println("Get data from async cache: " + key + "=" + futureValue);
	}
}
