$(document).ready(function() {

	$("#formRegistre").submit(function(event) {
		event.preventDefault();

		if (!validar())
			return;

		$.ajax({
			url : $("#formRegistre").attr('action'),
			type : 'post',
			data : $("#formRegistre").serialize()
		});
	});
});

function validar() {
	return !(!isUsernameValid() || !isEmailValid() || !isPasswordValid() || !isPassword2Valid());
}

function isUsernameValid() {
	var username = $('#username').val();
	if (username === "") {
		alert("El camp Nom d'usuari no pot estar buit");
		return false;
	}
	if (username.lenght > 20 || username.length < 3) {
		alert("El nom d'usuari ha de tenir entre 3 i 20 caràcters");
		return false;
	}
	return true;
}

function isEmailValid() {
	var email = $('#email').val();
	if (email === "") {
		alert("El camp email no pot estar buit");
		return false;
	}
	if (email.lenght > 50) {
		alert("L'email no pot superar els 50 caràcters");
		return false;
	}
	return true;
}

function isPasswordValid() {
	var password = $('#password').val();
	if (password === "") {
		alert("Has d'escollir una contrasenya");
		return false;
	}
	if (password.lenght > 20 || password.length < 3) {
		alert("La contrasenya ha de tenir entre 3 i 20 caràcters");
		return false;
	}
	return true;
}

function isPassword2Valid() {
	var password1 = $('#password').val();
	var password2 = $('#password2').val();
	if (password2 === "") {
		alert("Has de repetir la contrasenya");
		return false;
	}
	if (password2 !== password1) {
		alert("Les contrasenyes no coincideixen");
		return false;
	}
	return true;
}