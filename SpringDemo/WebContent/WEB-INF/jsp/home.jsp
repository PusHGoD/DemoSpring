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

<!-- Datepicker (CSS and JS) -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src='<c:url value="/Resources/js/custom.js"/>'></script>
<title>Home Page</title>
</head>
<body>
	<c:if test="${empty accountInfo}">
		<c:redirect url="/login.htm" />
	</c:if>
	<fmt:formatDate var="formatedDate" value="${accountInfo.dateOfBirth}"
		pattern="dd/MM/yyyy" />
	<div class="container">
		<div class="page-heading">
			<h4>
				Welcome, ${accountInfo.userName}! <a href="logout.htm" class="small">Log
					out</a>
			</h4>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading panel-title">
				<h3>Your information:</h3>
			</div>
			<div class="panel-body">
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">${successMessage}</div>
				</c:if>
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>
				<form>
					<div class="form-group">First name: ${accountInfo.firstName}</div>
					<div class="form-group">Last name: ${accountInfo.lastName}</div>
					<div class="form-group">Date of birth: ${formatedDate}</div>
					<input type="button" data-toggle="modal" data-target="#myModal"
						class="btn btn-control btn-lg" value="Edit" />
				</form>
				<form:form modelAttribute="user" action="home.htm" id="editForm">
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">×</button>
									<h4 class="modal-title">Edit profile</h4>
								</div>
								<div class="modal-body">
									<form:hidden path="id" value="${accountInfo.id}" />
									<form:hidden path="userName" value="${accountInfo.userName}" />
									<div class="form-group">
										First name:
										<form:input path="firstName" type="text" class="form-control"
											value="${accountInfo.firstName}" placeholder="First name"
											id="firstname" />
										<div id="firstname_error" class="text-danger"></div>
									</div>
									<div class="form-group">
										Last name:
										<form:input path="lastName" type="text" class="form-control"
											value="${accountInfo.lastName}" placeholder="Last name"
											id="lastname" />
										<div id="lastname_error" class="text-danger"></div>
									</div>
									<div class="form-group">
										Date of birth: <input type="text" name="dateOfBirth"
											data-provide="datepicker" class="form-control"
											data-date-format="dd/mm/yyyy" value="${formatedDate}"
											placeholder="dd/mm/yyyy" id="dob" />
										<div id="dob_error" class="text-danger"></div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
									<button type="button" class="btn btn-primary"
										onclick="submit_form();">Save</button>
								</div>

							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
					<!-- /.modal -->
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>