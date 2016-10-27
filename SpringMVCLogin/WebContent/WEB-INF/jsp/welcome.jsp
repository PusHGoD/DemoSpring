<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	
	<spring:form method = "post" commandName = "acc" action = "welcome.html">
		Username <spring:hidden path="username" value="${sessionScope.user.username}"/>${sessionScope.user.username} <br/>
		Firstname <spring:input path="fname" value="${sessionScope.user.fname }"/><br/>
		Lastname <spring:input path="lname" value="${sessionScope.user.lname }"/><br/>
		<spring:input type = "hidden" path="id" value="${sessionScope.user.id }"/><br/>
		<input type="submit" value="update">
	</spring:form>
</body>
</html>