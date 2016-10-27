<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<h1>Login</h1>
	${err}
	<spring:form method = "post" commandName = "acc" action = "login.html">
		Username <spring:input path="username"/><br>
		Password <spring:password path="password"/><br>
		<input type="submit" value="login">
	</spring:form>
</body>
</html>