package com.demo.senao.taskqueue;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.taskqueue.TaskQueuePb.TaskQueueQueryTasksResponse.Task;

/**
 * See also WEB-INF/queue.xml
 * @author simonsu
 *
 */
@SuppressWarnings("serial")
public class TaskQueueServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		String type = req.getParameter("type");
		String out = "";
		
		String key = req.getParameter("key");
		
		if(type != null)
		switch(type) {
		/**
		 * Test url:
		 * Add a task to default queue:
		 * curl 'http://localhost:8888/TaskQueue?type=add'
		 * Test default queue destination route:
		 * curl 'http://localhost:8888/TaskQueue?type=get'
		 * 
		 * Add a task to pull queue:
		 * curl 'http://localhost:8888/TaskQueue?type=add-pull'
		 * Lease pull queue:
		 * curl 'http://localhost:8888/TaskQueue?type=get-pull'
		 */
		case "add":
			// Using default Queue
			Queue q = QueueFactory.getDefaultQueue();
			// Post a url queue (if not set method, default queue use post as url fetch method)
			q.add(TaskOptions.Builder.withUrl("/TaskQueue")
			  .param("type", "get").method(Method.GET).taskName("Job" + new Date().getTime()));
			out = "Add task to queue ok...";
			break;
		case "add-pull":
			// 使用自訂Queue並在其中增加資料到pull queue
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Key k = KeyFactory.createKey("cp300ds", type);
			Entity entity = new Entity(type, "test123");
			Transaction txn = ds.beginTransaction();
			try {
				entity.setProperty("name", "jelly");
				entity.setProperty("sex","F");
				ds.put(entity);
				q = QueueFactory.getQueue("ProcessedOrderQueue");
				q.add(TaskOptions.Builder.withMethod(Method.PULL).payload("hello world"));
				txn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(txn.isActive()) txn.rollback();
			}
			out = "do add pull done...";
			break;
		case "get": //pull queue response
			doLog("TaskQueueServlet got a get param event...");
			break;
		case "get-pull": //lease then delete
			q = QueueFactory.getQueue("ProcessedOrderQueue");
			//Lease then delete:
			List tasks = q.leaseTasks(3600, TimeUnit.SECONDS, 100);
			doLog("Get tasks total:" + tasks.size());
			//TODO: Do work with returned tasks
			Iterator<TaskHandle> iter = tasks.iterator();
			while(iter.hasNext()) {
				TaskHandle t = (TaskHandle) iter.next();
				doLog("do pull queue job...");
				doLog("Queue name:" + t.getQueueName());
				doLog("Task name:" + t.getName());
				//Delete task after work done...
				q.deleteTask(t.getName());
			}
			out = "do get-pull done....";
			break;
		case "purge":
			//刪除特定task
			QueueFactory.getQueue("ProcessedOrderQueue").deleteTask("bar");
			out = "purge queue ProcessedOrderQueue done...";
			break;
		case "purge-all":
			//刪除所有
			QueueFactory.getQueue("ProcessedOrderQueue").purge();
			out = "purge ProcessedOrderQueue all done...";
			break;
		default:
			out = "Nothing to show..";
			break;
		}
		else
			out = "type is null...";
		
		resp.getWriter().println( out );
	}

	private void doLog(String msg) {
		// TODO Auto-generated method stub
		System.out.println("[" + new Date().toString() + "] " + msg);
	}
	
}
