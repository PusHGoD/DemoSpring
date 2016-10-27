function checkLoginInput() {
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var usernameError = document.getElementById("username_error");
	var passwordError = document.getElementById("password_error");
	var result = true;
	if (username == null || username == "") {
		usernameError.innerHTML = "Please enter username.";
		result = false;
	} else if (username.length > 20) {
		usernameError.innerHTML = "Username is too long.";
		result = false;
	} else {
		usernameError.innerHTML = "";
	}
	if (password == null || password == "") {
		passwordError.innerHTML = "Please enter password.";
		result = false;
	} else if (password.length > 20) {
		passwordError.innerHTML = "Password is too long.";
		result = false;
	} else {
		passwordError.innerHTML = "";
	}
	return result;
}

function parseDate(input, format) {
	format = format || 'dd/mm/yyyy'; // somedefault format
	var parts = input.match(/(\d+)/g), i = 0, fmt = {};
	// extract date-part indexes from the format
	format.replace(/(yyyy|dd|mm)/g, function(part) {
		fmt[part] = i++;
	});
	return new Date(parts[fmt['yyyy']], parts[fmt['mm']] - 1, parts[fmt['dd']]);
}

function checkUpdateInput() {
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var dob = document.getElementById("dob").value;
	var firstnameError = document.getElementById("firstname_error");
	var lastnameError = document.getElementById("lastname_error");
	var dobError = document.getElementById("dob_error");
	var result = true;
	if (firstname == null || firstname == "") {
		firstnameError.innerHTML = "Please enter first name.";
		result = false;
	} else if (firstname.length > 30) {
		firstnameError.innerHTML = "First name is too long.";
		result = false;
	} else {
		firstnameError.innerHTML = "";
	}
	if (lastname == null || lastname == "") {
		lastnameError.innerHTML = "Please enter last name.";
		result = false;
	} else if (lastname.length > 30) {
		lastnameError.innerHTML = "Last name is too long.";
		result = false;
	} else {
		lastnameError.innerHTML = "";
	}
	if (dob == null || dob == "") {
		dobError.innerHTML = "Please enter date of birth.";
		result = false;
	} else {
		var date = Date.parse(parseDate(dob, "dd/mm/yyyy"));
		if (!isNaN(date.getTime())) {
			dobError.innerHTML = "Date of birth is not in correct format.";
			result = false;
		} else {
			dobError.innerHTML = "";
		}
	}
	return result;
}
