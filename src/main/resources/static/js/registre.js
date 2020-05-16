$(document).ready(function () {

	$('input').on('keydown', function () {
		$(this).removeClass('invalid');
	});

	$("#form-submit-button").click(function(event) {
		if (!validar()) return;

		$("form").submit();
	});
});

function validar() {
	return !(!isUsernameValid() || !isEmailValid() || !isPasswordValid() || !isPassword2Valid());
}

function isUsernameValid() {
	var $input = $('#username');
	var helper = $('#helper-username')[0];
	var username = $input.val();
	if (username === "") {
		$input.addClass('invalid');
		helper.dataset.error = "El nom d'usuari no pot estar buit";
		return false;
	}
	if (username.length > 20 || username.length < 3) {
		$input.addClass('invalid');
		helper.dataset.error = "El nom d'usuari ha de tenir entre 3 i 20 caràcters";
		return false;
	}
	return true;
}

function isEmailValid() {
	var $input = $('#email');
	var helper = $('#helper-email')[0];
	var email = $input.val();
	if (email === "") {
		$input.addClass('invalid');
		helper.dataset.error = "L'email no pot estar buit";
		return false;
	}
	if (email.length > 50) {
		$input.addClass('invalid');
		helper.dataset.error = "L'email no pot superar els 50 caràcters";
		return false;
	}
	return true;
}

function isPasswordValid() {
	var $input = $('#password');
	var helper = $('#helper-password-1')[0];
	var password = $input.val();
	if (password === "") {
		$input.addClass('invalid');
		helper.dataset.error = "Has d'escollir una contrasenya";
		return false;
	}
	if (password.length > 20 || password.length < 3) {
		$input.addClass('invalid');
		helper.dataset.error = "La contrasenya ha de tenir entre 3 i 20 caràcters";
		return false;
	}
	return true;
}

function isPassword2Valid() {
	var $input1 = $('#password');
	var $input2 = $('#password2');
	var helper1 = $('#helper-password-1')[0];
	var helper2 = $('#helper-password-2')[0];
	var password1 = $input1.val();
	var password2 = $input2.val();
	if (password2 === "") {
		$input2.addClass('invalid');
		helper2.dataset.error = "Has de repetir la contrasenya";
		return false;
	}
	if (password2 !== password1) {
		$input2.addClass('invalid');
		helper2.dataset.error = "Les contrasenyes no coincideixen";
		return false;
	}
	return true;
}