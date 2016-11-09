$(document).ready(function() {
	$("#login_btn").click(function() {
		return checkLoginInput();
	});

	$("#user_update_btn").click(function() {
		if (checkUpdateInput()) {
			$("#editForm").submit();
		}
	});

	$('input.edit-btn').click(function() {
		if ($(this).val() == "Edit") {
			var dad = $(this).parent().parent();
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
			dad.find('input.edit-btn').val("Save");
		} else if ($(this).val() == "Save") {
			var dad = $(this).parent().parent();
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
		}
	});

	// $('input.user-name-textbox').focusout(function() {
	// var dad = $(this).parent();
	// $(this).hide();
	// if ($(this).val() != "") {
	// dad.find('span.user-name').text($(this).val());
	// } else {
	// $(this).val(dad.find('span.user-name').text());
	// }
	// dad.find('span.user-name').show();
	// dad.find('input.edit-btn').val("Edit");
	// });
});

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
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
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
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var dob = document.getElementById("dob").value;
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
