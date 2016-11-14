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
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
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
<!-- Custom javascript file (mostly validation) -->
<script type="text/javascript"
	src='<c:url value="/Resources/js/custom.js"/>'></script>
<title>Manager Page</title>
</head>
<body>
	<!-- If user has not logged in yet, redirect to login page -->
	<c:if test="${empty accountInfo or accountInfo.role.id != 1}">
		<c:redirect url="redirect.jsp" />
	</c:if>
	<!-- Container div -->
	<div class="container">
		<!-- Page header -->
		<div class="page-heading">
			<h4>
				Welcome, ${accountInfo.userName}! <a href="logout.htm" class="small">Log
					out</a>
			</h4>
		</div>
		<!-- Employee list panel -->
		<div class="panel panel-primary">
			<!-- Panel title -->
			<div class="panel-heading panel-title">
				<h3>Employee list:</h3>
			</div>
			<!-- Panel body -->
			<div class="panel-body">
				<div id="ajaxMessage"></div>
				<!-- Div conatining information -->
				<div>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Username</th>
								<th>First name</th>
								<th>Last name</th>
								<th>Date of birth</th>
								<th>Email</th>
								<th>Active</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="account" items="${accountList}">
								<!-- Format date to dd/mm/yyyy -->
								<fmt:formatDate var="formatedDate"
									value="${account.dateOfBirth}" pattern="dd/MM/yyyy" />

								<tr>
									<form:form modelAttribute="account" action="edit.htm"
										class="management-form" method="POST">
										<td><form:hidden path="id" value="${account.id}" />${account.id}</td>
										<td><span class="user-name">${account.userName}</span> <form:input
												path="userName" class="form-control user-name-textbox"
												placeholder="Username" style="display: none"
												value="${account.userName}" /></td>
										<td><span class="first-name">${account.firstName}</span>
											<form:input path="firstName" type="text"
												class="form-control first-name-textbox"
												value="${account.firstName}" placeholder="First name"
												id="password" style="display: none" /></td>
										<td><span class="last-name">${account.lastName}</span> <form:input
												path="lastName" type="text"
												class="form-control last-name-textbox"
												value="${account.lastName}" placeholder="Last name"
												id="lastname" style="display: none" /></td>
										<td><span class="date-of-birth">${formatedDate}</span> <form:input
												path="dateOfBirth" data-provide="datepicker"
												class="form-control dob-datepicker"
												data-date-format="dd/mm/yyyy" value="${formatedDate}"
												placeholder="dd/mm/yyyy" id="dob" style="display: none" /></td>
										<td><span class="email">${account.email}</span> <form:input
												path="email" type="text" class="form-control email-textbox"
												value="${account.email}" placeholder="Email" id="email"
												style="display: none" /></td>
										<td><c:if test="${account.active}">
												<span class="active"><strong class="text-success">Active</strong></span>
											</c:if> <c:if test="${!account.active}">
												<span class="active"><strong class="text-danger">Inactive</strong></span>
											</c:if> <form:select path="active"
												class="form-control active-combobox" style="display: none">
												<form:option value="true">Active</form:option>
												<form:option value="false">Inactive</form:option>
											</form:select></td>
										<td><div class="action-btns">
												<input type="button" class="btn btn-primary edit-btn"
													value="Edit" /> <input type="button"
													class="btn btn-warning" value="Reset" data-toggle="modal"
													data-target="#resetModal${account.id}" />
											</div>
											<div class="edit-btns" style="display: none">
												<input type="submit" class="btn btn-primary save-btn"
													value="Save" /> <input type="button"
													class="btn btn-link cancel-btn" value="Cancel" />
											</div>
											<div class="modal fade" id="resetModal${account.id}"
												tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
												aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<!-- Modal header -->
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-hidden="true">×</button>
															<h4 class="modal-title">Reset password</h4>
														</div>
														<!-- Modal body -->
														<div class="modal-body">Do you want to reset
															password for this account?</div>
														<!-- Modal footer -->
														<div class="modal-footer">
															<input type="button" class="btn btn-primary reset-btn"
																value="Yes" formaction="reset.htm" data-dismiss="modal" />
															<button type="button" class="btn btn-danger"
																data-dismiss="modal">No</button>
														</div>
													</div>
													<!-- /.modal-content -->
												</div>
												<!-- /.modal-dialog -->
											</div> <!-- /.modal --></td>
									</form:form>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input type="button" class="btn btn-primary" value="Add user"
						data-toggle="modal" data-target="#editModal" />
				</div>
			</div>
		</div>
	</div>
	<!-- Edit modal form -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form:form modelAttribute="account" action="add.htm" id="addForm">
					<!-- Modal header -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">Edit user</h4>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<!-- User name -->
						<div class="form-group">
							User name:
							<form:input path="userName" type="text" class="form-control"
								value="" placeholder="User name" id="username" />
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- First name -->
						<div class="form-group">
							First name:
							<form:input path="firstName" type="text" class="form-control"
								value="" placeholder="First name" id="firstname" />
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- Last name -->
						<div class="form-group">
							Last name:
							<form:input path="lastName" type="text" class="form-control"
								value="" placeholder="Last name" id="lastname" />
							<div id="lastname_error" class="text-danger"></div>
						</div>
						<!-- Date of birth -->
						<div class="form-group">
							Date of birth:
							<!-- Date picker -->
							<form:input path="dateOfBirth" type="text"
								data-provide="datepicker" class="form-control"
								data-date-format="dd/mm/yyyy" value="" placeholder="dd/mm/yyyy"
								id="dob" />
							<div id="dob_error" class="text-danger"></div>
						</div>
						<!-- Email -->
						<div class="form-group">
							Email:
							<form:input path="email" type="text" class="form-control"
								value="" placeholder="Email" id="email" />
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- Active -->
						<div class="form-group">
							Active:
							<form:radiobutton path="active" value="true" />
							<strong class="text-success">Active</strong>
							<form:radiobutton path="active" value="false" />
							<strong class="text-danger">Inactive</strong>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						<button type="button" class="btn btn-primary" id="manager-add-btn">Save</button>
					</div>
				</form:form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</body>
</html>