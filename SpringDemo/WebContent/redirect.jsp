<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- Redirect to home page -->
	<c:if test="${not empty accountInfo and empty inactiveAccountName }">
		<c:redirect url="/home.htm" />
	</c:if>
	<!-- Redirect to login page -->
	<c:if test="${empty accountInfo and empty inactiveAccountName }">
		<c:redirect url="/login.htm" />
	</c:if>
</body>
</html>