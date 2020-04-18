$(document).ready(function() {

	$("#formModificarDades").submit(function(event) {
		event.preventDefault();

		if (!validar())
			return;

		$.ajax({
			url : $("#formModificarDades").attr('action'),
			type : 'put',
			data : $("#formModificarDades").serialize(),
//			cache : false,
//			contentType : false,
//			processData : false,
//			enctype : 'multipart/form-data',
		});

		window.location = "/home";
	});
});

function validar() {
	return !(!isUsernameValid() || !isEmailValid() || 
			!isNameValid() || !isLastNameValid() ||
			!isPasswordValid() || !isPassword2Valid());
// !isLocationValid() || isPictureFileValid()
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

function isNameValid() {
	var name = $('#name').val();
	if (name !== "") {
		if (name.lenght > 50 || name.length < 3) {
			alert("El nom ha de tenir entre 3 i 50 caràcters");
			return false;
		}
	}
	return true;
}

function isLastNameValid() {
	var lastName = $('#lastName').val();
	if (lastName !== "") {
		if (lastName.lenght > 50 || lastName.length < 3) {
			alert("El cognom ha de tenir entre 3 i 50 caràcters");
			return false;
		}
	}
	return true;
}


function isLocationValid() {
	//Ara mateix demana un codi postal
}

function isPictureFileValid() {
	//Encara no carrega la imatge correctament
}

function isPasswordValid() {
	var password = $('#password').val();
	if (password !== "") {
		if (password.lenght > 20 || password.length < 3) {
			alert("La contrasenya ha de tenir entre 3 i 20 caràcters");
			return false;
		}
	}
	return true;
}

function isPassword2Valid() {
	var password1 = $('#password').val();
	var password2 = $('#password2').val();
	if (password1 !== "") {
		if (password2 === "") {
			alert("Has de repetir la contrasenya");
			return false;
		}
		if (password2 !== password1) {
			alert("Les contrasenyes no coincideixen");
			return false;
		}
	}
	return true;
}