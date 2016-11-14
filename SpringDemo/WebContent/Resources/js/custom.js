$(document).ready(function() {
	$("#login-btn").click(function() {
		return checkLoginInput();
	});

	$("#user-update-btn").click(function() {
		if (checkUpdateInput()) {
			$("#editForm").submit();
		}
	});

	$('input.edit-btn').click(function() {
		if ($(this).val() == "Edit") {
			var dad = $(this).parent().parent().parent();
			dad.find('span.user-name').hide();
			dad.find('span.password').hide();
			dad.find('span.first-name').hide();
			dad.find('span.last-name').hide();
			dad.find('span.date-of-birth').hide();
			dad.find('span.email').hide();
			dad.find('span.active').hide();
			dad.find('input.user-name-textbox').show();
			dad.find('input.password-textbox').show();
			dad.find('input.first-name-textbox').show();
			dad.find('input.last-name-textbox').show();
			dad.find('input.dob-datepicker').show();
			dad.find('input.email-textbox').show();
			dad.find('select.active-combobox').show();
			dad.find('div.action-btns').hide();
			dad.find('div.edit-btns').show();
		}
	});

	$('input.cancel-btn').click(function() {
		if ($(this).val() == "Cancel") {
			var dad = $(this).parent().parent().parent();
			dad.find('input.user-name-textbox').hide();
			dad.find('input.password-textbox').hide();
			dad.find('input.first-name-textbox').hide();
			dad.find('input.last-name-textbox').hide();
			dad.find('input.dob-datepicker').hide();
			dad.find('input.email-textbox').hide();
			dad.find('select.active-combobox').hide();
			dad.find('span.user-name').show();
			dad.find('span.password').show();
			dad.find('span.first-name').show();
			dad.find('span.last-name').show();
			dad.find('span.date-of-birth').show();
			dad.find('span.email').show();
			dad.find('span.active').show();
			dad.find('input.edit-btn').val("Edit");
			dad.find('div.action-btns').show();
			dad.find('div.edit-btns').hide();
		}
	});

	$("#manager-add-btn").click(function() {
		$("#addForm").submit();
	});

	$("input.reset-btn").click(function() {
		var formDad = $(this).closest("tr");
		var data = formDad.find(".management-form").serializeArray();
		var data2JSON = {};
		$.each(data, function(v) {
			if (this.value != null) {
				v = this.value;
			} else {
				v = '';
			}
			if (this.name == "dateOfBirth") {
				v = parseDate(v, "dd/mm/yyyy");
			}
			if (data2JSON[this.name] != null) {
				if (!data2JSON[this.name].push) {
					data2JSON[this.name] = [ data2JSON[this.name] ];
				}
				data2JSON[this.name].push(v);
			} else {
				data2JSON[this.name] = v;
			}
		});
		$.ajax({
			url : 'reset.htm',
			type : "POST",
			contentType : "application/json; charset=utf-8",
			dataType : "text",
			data : JSON.stringify(data2JSON),
			success : function(response) {
				$("#ajaxMessage")
						.html(
								"<div class='alert alert-success'>"
										+ response
										+ "</div>");
			},
			error : function(response) {
				$("#ajaxMessage")
						.html(
								"<div class='alert alert-danger'>"
										+ response
										+ "</div>");
			}
		});
	});

	window.actionEvents = {
		'click .edit' : function(e, value, row, index) {
			alert('You click edit icon, row: ' + JSON.stringify(row));
			console.log(value, row, index);
		},
		'click .reset' : function(e, value, row, index) {
			$.ajax({
				url : 'reset.htm',
				type : "POST",
				contentType : "application/json; charset=utf-8",
				dataType : "text",
				data : JSON.stringify(row),
				success : function(response) {
					$("#ajaxMessage")
							.html(
									"<div class='alert alert-success'>"
											+ response
											+ "</div>");
				},
				error : function(response) {
					$("#ajaxMessage")
							.html(
									"<div class='alert alert-danger'>"
											+ response
											+ "</div>");
				}
			});
		}
	};
});

function load() {
	$.ajax({
		url : 'list.htm',
		type : "GET",
		headers: {
            Accept: 'application/json'
        },
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(list) {
			$('#table').bootstrapTable({
				data : list
			});
		},
		error : function(list) {
			
		}
	});
}

function dateFormatter(value, row, index) {
	 var date = new Date(value);
	 var mm = date.getMonth()+1;
	 var dd = date.getDate();
	 var yyyy = new String(date.getFullYear());
	 if (mm < 10) { 
	    mm = "0"+mm;
	 }
	 if (dd < 10) {
	    dd = "0"+dd;
	  }
	 return dd+"/"+mm+"/"+yyyy;
}

function actionFormatter(value, row, index) {
	return [ '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
			'<i class="glyphicon glyphicon-edit"></i>', '</a>',
			'<a class="reset ml10" href="javascript:void(0)" title="Reset">',
			'<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}

function parseDate(input, format) {
	if (input.match("\\d{1,2}[/]\\d{1,2}[/]\\d{1,4}")) {
		format = format || 'dd/mm/yyyy';
		var parts = input.match(/(\d+)/g), i = 0, fmt = {};
		format.replace(/(yyyy|dd|mm)/g, function(part) {
			fmt[part] = i++;
		});
		return new Date(parts[fmt['yyyy']], parts[fmt['mm']] - 1,
				parts[fmt['dd']]);
	} else {
		return null;
	}
}

function checkLoginInput() {
	var username = $("#username").val();
	var password = $("#password").val();
	var result = true;
	if (username == null || username == "") {
		$("#username_error").html("Please enter username.");
		result = false;
	} else if (username.length > 20) {
		$("#username_error").html("Username is too long.");
		result = false;
	} else {
		$("#username_error").html("");
	}
	if (password == null || password == "") {
		$("#password_error").html("Please enter password.");
		result = false;
	} else if (password.length > 20) {
		$("#password_error").html("Password is too long.");
		result = false;
	} else {
		$("#password_error").html("");
	}
	return result;
}

function checkUpdateInput() {
	var firstname = $("#firstName").val();
	var lastname = $("#lastName").val();
	var dob = $("#dateOfBirth").val();
	var result = true;
	if (firstname == null || firstname == "") {
		$("#firstname_error").html("Please enter first name.");
		result = false;
	} else if (firstname.length > 30) {
		$("#firstname_error").html("First name is too long.");
		result = false;
	} else {
		$("#firstname_error").html("");
	}
	if (lastname == null || lastname == "") {
		$("#lastname_error").html("Please enter last name.");
		result = false;
	} else if (lastname.length > 30) {
		$("#lastname_error").html("Last name is too long.");
		result = false;
	} else {
		$("#lastname_error").html("");
	}

	if (dob == null || dob == "") {
		$("#dob_error").html("Please enter date of birth.");
		result = false;
	} else {
		var date = parseDate(dob, "dd/mm/yyyy");
		if (date == null) {
			$("#dob_error").html("Date of birth is not in correct format.");
			result = false;
		} else {
			var today = new Date();
			today.setDate(today.getDate() - 1);
			if (date >= today) {
				$("#dob_error").html(
						"Date of birth cannot be later than today.");
				result = false;
			} else {
				$("#dob_error").html("");
			}
		}
	}
	return result;
}