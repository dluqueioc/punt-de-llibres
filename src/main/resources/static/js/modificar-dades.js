$(document).ready(function() {

	M.updateTextFields();
	$("label[for!='nature']").addClass('active');

	//$('label').addClass('active');
	/*
	if ($('#location').val()!=''){
		$("label[for!='nature']").addClass('active');
	}
	

	$('#location').change(function () {
		if ($('#location').val()!=''){
			$("label[for='location']").addClass('active');
		} else {
			$("label[for='location']").removeClass('active');
		}
	});

	
	$('#location').on('input',function(){
		if ($('#location').val()!='' || $('#location').is(":focus")){
			$("label[for='location']").addClass('active');
		} else {
			$("label[for='location']").removeClass('active');
		}
	});
	
	$('#location').on('change',function(){
		if ($('#location').val()!='' || $('#location').is(":focus")){
			$("label[for='location']").addClass('active');
		} else {
			$("label[for='location']").removeClass('active');
		}
	});
	


	$('#location').focus(function () {
		if ($('#location').val()!=''){
			$("label[for='location']").addClass('active');
		} else {
			$("label[for='location']").removeClass('active');
		}
	});
	 */
	
    const elems = document.querySelectorAll('.modal');
    const [modal, ...rest] = M.Modal.init(elems, { dismissible: true });

    $('#selFoto').change(function () {
        readURL(this);
    });

    $('#save-button').click(function (event) {
        if (!validar()) return;

        $('form').submit();
    });

	//inicialització del selector d'adreces
	var placesAutocomplete = places({
		appId : 'pl7J5DC7ND0H',
		apiKey : 'd273df1e10d81101379150ec352c11c3',
		container : document.querySelector('#location'),
	//style: false
	}).configure({
		type : 'address',
		countries : 'es',
	});

	//assignació del valor del punt
	placesAutocomplete.on('change', function resultSelected(e) {
		//$point = "(" + e.suggestion.latlng.lat + "," + e.suggestion.latlng.lng + ")";
		$('#geoLocationLat').val(e.suggestion.latlng.lat);
		$('#geoLocationLng').val(e.suggestion.latlng.lng);
	});

});

    function validar() {
        return !(
            !isEmailValid() ||
            !isNameValid() ||
            !isLastNameValid() ||
            !isPasswordValid()
        );
        // !isLocationValid() || isPictureFileValid()
    }

    function isEmailValid() {
        var email = $('#email').val();
        if (email === '') {
            alert('El camp email no pot estar buit');
            return false;
        }
        if (email.length > 50) {
            alert("L'email no pot superar els 50 caràcters");
            return false;
        }
        return true;
    }

    function isNameValid() {
        var name = $('#name').val();
        if (name !== '') {
            if (name.length > 50 || name.length < 3) {
                alert('El nom ha de tenir entre 3 i 50 caràcters');
                return false;
            }
        }
        return true;
    }

    function isLastNameValid() {
        var lastName = $('#lastName').val();
        if (lastName !== '') {
            if (lastName.length > 50 || lastName.length < 3) {
                alert('El cognom ha de tenir entre 3 i 50 caràcters');
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
        if (password.length > 20 || password.length < 3) {
            modal.open();
            return false;
        }
        return true;
    }

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#imgPerfil').attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
});
