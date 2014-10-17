<%@ page language="java" contentType="text/html; charset=UTF8"
    pageEncoding="UTF8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>Insert title here</title>
</head>
<body>

<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
    UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	/* if (user != null) {
	  pageContext.setAttribute("user", user);
	} */
%>

<%if(user != null){ %>
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></p>
<h1>Welcome to GAE!</h1>
<%} else { %>
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
<h1>Please login first...</h1>
<%} %>
</body>
</html>