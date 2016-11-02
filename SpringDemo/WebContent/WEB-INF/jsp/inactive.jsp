<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inactive account</title>
</head>
<body>
	<!-- If user is not logged in or is not inactive, redirect to login or home page -->
	<c:if test="${empty inactiveAccountName}">
		<c:redirect url="redirect.jsp" />
	</c:if>
	<div class="container">
		<!-- Warning panel -->
		<c:if test="${not empty inactiveAccountName}">
			<div class="alert alert-warning">Hello ${inactiveAccountName},
				your account is currently inactive. Please contact support to
				activate your account!</div>
			<form:form action="logout.htm">
				<input type="submit" class="btn btn-default"
					value="Click here to log out" />
			</form:form>
		</c:if>
	</div>
</body>
</html>