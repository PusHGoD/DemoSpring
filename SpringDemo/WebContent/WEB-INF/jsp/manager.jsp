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

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/extensions/filter/bootstrap-table-filter.js" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/extensions/filter-control/bootstrap-table-filter-control.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/bootstrap-table.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/bootstrap-table.js"></script>
<!-- <link -->
<!-- 	href="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" -->
<!-- 	rel="stylesheet" /> -->
<!-- <script -->
<!-- 	src="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script> -->
<!-- <script -->
<!-- 	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/extensions/editable/bootstrap-table-editable.js"></script> -->

<!-- Custom javascript file (mostly validation) -->
<script type="text/javascript"
	src='<c:url value="/Resources/js/custom.js"/>'></script>
<title>Manager Page</title>
</head>
<body id="managerPage">
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
					<div id="toolbar" class="btn-group">
						<button type="button" id="addButton" class="btn btn-default"
							data-toggle="modal" data-target="#addModal">
							<i class="glyphicon glyphicon-plus"></i>
						</button>
						<button type="button" id="editButton" class="btn btn-default"
							data-toggle="modal" data-target="#editModal" disabled="disabled">
							<i class="glyphicon glyphicon-edit"></i>
						</button>
					</div>
					<table id="table" class="table" data-method="POST"
						data-show-toggle="true" data-content-type="application/json"
						data-toolbar="#toolbar">
						<thead>
							<tr>
								<th data-field="id">ID</th>
								<th data-field="userName">Username</th>
								<th data-field="firstName">First name</th>
								<th data-field="lastName">Last name</th>
								<th data-field="dateOfBirth" data-formatter="dateFormatter">Date
									of birth</th>
								<th data-field="email">Email</th>
								<th data-field="active">Active</th>
								<th data-field="action" data-formatter="actionFormatter"
									data-events="actionEvents"></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- Add modal form -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form:form modelAttribute="account" action="add.htm" id="addForm">
					<!-- Modal header -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">Add user</h4>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<!-- User name -->
						<div class="form-group">
							User name:
							<form:input path="userName" type="text" class="form-control"
								value="" placeholder="User name"/>
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- First name -->
						<div class="form-group">
							First name:
							<form:input path="firstName" type="text" class="form-control"
								value="" placeholder="First name"/>
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- Last name -->
						<div class="form-group">
							Last name:
							<form:input path="lastName" type="text" class="form-control"
								value="" placeholder="Last name"/>
							<div id="lastname_error" class="text-danger"></div>
						</div>
						<!-- Date of birth -->
						<div class="form-group">
							Date of birth:
							<!-- Date picker -->
							<form:input path="dateOfBirth" type="text"
								data-provide="datepicker" class="form-control"
								data-date-format="dd/mm/yyyy" value="" placeholder="dd/mm/yyyy" />
							<div id="dob_error" class="text-danger"></div>
						</div>
						<!-- Email -->
						<div class="form-group">
							Email:
							<form:input path="email" type="text" class="form-control"
								value="" placeholder="Email" />
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- Active -->
						<div class="form-group">
							Active:
							<form:radiobutton path="active" value="true" checked="true" />
							<strong class="text-success">Active</strong>
							<form:radiobutton path="active" value="false"/>
							<strong class="text-danger">Inactive</strong>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						<button type="button" class="btn btn-primary" id="manager-add-btn" data-dismiss="modal">Add</button>
					</div>
				</form:form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- Edit modal form -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form:form modelAttribute="account" action="edit.htm" id="editForm">
					<!-- Modal header -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">Edit user</h4>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<form:hidden path="id" value="" />
						<!-- User name -->
						<div class="form-group">
							User name:
							<form:input path="userName" type="text" class="form-control"
								value="" placeholder="User name" />
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- First name -->
						<div class="form-group">
							First name:
							<form:input path="firstName" type="text" class="form-control"
								value="" placeholder="First name" />
							<div id="firstname_error" class="text-danger"></div>
						</div>
						<!-- Last name -->
						<div class="form-group">
							Last name:
							<form:input path="lastName" type="text" class="form-control"
								value="" placeholder="Last name" />
							<div id="lastname_error" class="text-danger"></div>
						</div>
						<!-- Date of birth -->
						<div class="form-group">
							Date of birth:
							<!-- Date picker -->
							<form:input path="dateOfBirth" type="text"
								data-provide="datepicker" class="form-control"
								data-date-format="dd/mm/yyyy" value="" placeholder="dd/mm/yyyy" />
							<div id="dob_error" class="text-danger"></div>
						</div>
						<!-- Email -->
						<div class="form-group">
							Email:
							<form:input path="email" type="text" class="form-control"
								value="" placeholder="Email" />
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
						<button type="button" class="btn btn-primary" id="manager-edit-btn" data-dismiss="modal">Update</button>
					</div>
				</form:form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</body>
</html>