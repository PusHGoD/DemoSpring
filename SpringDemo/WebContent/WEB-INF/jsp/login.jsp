<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

<!-- Latest compiled bootstrap JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Custom javascript file (mostly validation) -->
<script type="text/javascript"
	src='<c:url value="/Resources/js/custom.js"/>'></script>
<title>Login Page</title>
</head>
<body>
	<!-- If user has logged in, redirect to home page or inactive pages-->
	<c:if test="${not empty accountInfo or not empty inactiveAccountName}">
		<c:redirect url="redirect.jsp" />
	</c:if>
	<!-- Container div -->
	<div class="container">
		<!-- Header -->
		<h1>Login</h1>
		<!-- Error message -->
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger">${errorMessage}</div>
		</c:if>
		<!-- Login form -->
		<form:form modelAttribute="user" action="login.htm">
			<!-- User name -->
			<div class="form-group">
				Username:
				<form:input type="text" path="userName" class="form-control"
					placeholder="Username" id="username" />
				<div id="username_error" class="text-danger"></div>
			</div>
			<!-- Password -->
			<div class="form-group">
				Password:
				<form:password path="password" class="form-control"
					placeholder="Password" id="password" />
				<div id="password_error" class="text-danger"></div>
			</div>
			<!-- Login button -->
			<input type="submit" value="Login"
				class="btn btn-primary btn-lg btn-block"
				onclick="return checkLoginInput();" />
		</form:form>
	</div>
</body>
</html>