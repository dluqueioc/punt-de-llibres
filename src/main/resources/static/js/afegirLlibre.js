$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $('select').material_select();
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

//    $("#selArxiu").change(function () {
//        readURL(this);
//    });
    $("#formAfegirLlibre").submit(function (event) {
        event.preventDefault();
        if (! validar()) return;
        var data = {
            title: $('[name=title]').val(),
            isbn: $('[name=isbn]').val() || null,
            authorName: $('[name=authorName]').val(),
            publisherName: $('[name=publisherName]').val(),
            genreId : $('[name=genreId]').val(),
            //theme: $('[name=theme]').val(),
            languageId: $('[name=languageId]').val()
            //preservation: $('[name=preservation]').val() || null,
            //edition: $('[name=edition]').val() || null,
        }
        $.ajax({
            type: "POST",
            url: '/api/books',
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            enctype: 'multipart/form-data',
            dataType: 'json',
        })
            .then(() => {
                var another = confirm('Llibre introduït correctament. Vols introduir un altre llibre?');
                if (! another) {
                    location = '/';
                }
            })
            .fail(() => alert('S\'ha produït un error'));
    });
});

function validar() {
    return !(!esTitolValid() || !esValidISBN() ||
        !esValidAutor() || !esValidEditorial() ||
        !esValidGenere() || !esValidTematica() ||
        !esValidIdioma() || !esValidEstatConserv()
        //|| !esValidEdicio()
        //|| !esValidArxiu()
    );
}

function esTitolValid() {
    var titol = $('#titol').val();
    if (titol === "") {
        alert("El camp títol no pot estar buit");
        return false;
    }
    if (titol.length > 100) {
        alert("El títol no pot ocupar més de 100 caràcters");
        return false;
    }
    return true;
}

function esValidAutor() {
    var autor = $('#autor').val();
    if (autor === "") {
        alert("El camp autor no pot estar buit");
        return false;
    }
    return true;
}

function esValidEditorial() {
    if ($('#editorial').find(":selected").text() == "Editorial") {
        alert("Has d'escollir una editorial");
        return false;
    }
    return true;
}

function esValidGenere() {
    if ($('#genere').find(":selected").text() == "Gènere") {
        alert("Has d'escollir un gènere");
        return false;
    }
    return true;
}

function esValidTematica() {
    if ($('#estil').find(":selected").text() == "Temàtica") {
        alert("Has d'escollir una temàtica");
        return false;
    }
    return true;
}

function esValidIdioma() {
    if ($('#idioma').find(":selected").text() == "Idioma") {
        alert("Has d'escollir un idioma");
        return false;
    }
    return true;
}

function esValidEstatConserv() {
    var estatConserv = $('#estatConserv').val();

    if (estatConserv === "") {
        return true;
    }
    if (estatConserv.length > 100) {
        alert("La descripció de l'estat de conservació no pot superar els 100 caràcters");
        return false;
    }
    return true;
}

function esValidEdicio() {
    var edicio = $('#edicio').val();
    if (edicio === "") {
        alert("El camp edició no pot estar buit");
        return false;
    }
    return true;
}

function esValidArxiu() {
    if ($('#selArxiu')[0].files.length === 0) {
        alert("No has escollit una imatge de perfil!");
        return false;
    }
    if ($('#selArxiu')[0].files.length > 0) {
        var arxiu = $('#selArxiu')[0].files[0];
        var fileType = arxiu["type"];
        var validImageTypes = ["image/gif", "image/jpeg", "image/png"];
        if ($.inArray(fileType, validImageTypes) < 0) {
            alert("No es un tipus d'arxiu vàlid");
            return false;
        }
    }
    return true;
}

function esValidISBN() {
    var isbn = $('#ISBN').val();
    var result = false;

    if (isbn.length > 0) {
        isbn = isbn.replace( /-/g, "" ); // remove '-' symbols
        isbn = isbn.replace( / /g, "" ); // remove whiteSpace

        switch (isbn.length) {
        	case 10 :
        		result = isValidISBN10(isbn);
        		break;
        	case 13 :
        		result = isValidISBN13(isbn);
        		break;
        }
        if (!result) {
            alert("L'ISBN introduït no és vàlid");
        }
        return result;
    }
    return true;
}

function isValidISBN10(isbn) {
	var result = false;

	// ^ - start string
	// \d - digit
	// {9} - nine
	// \d{9} - nine digits
	// (\d|X) - digit or 'X' char
	// (\d|X){1} - one digit or 'X' char
	// $ - end string
	var regex = new RegExp( /^\d{9}(\d|X){1}$/ );

	if ( regex.test( isbn ) ) {
		var sum = 0;


    // result = (isbn[0] * 1 + isbn[1] * 2 + isbn[2] * 3 + isbn[3] * 4 + ... + isbn[9] * 10) mod 11 == 0
		for ( var i = 0; i < 9; i++ ) {
			sum += isbn[i] * (i+1);
		}
		sum += isbn[9] == 'X' ? 10 : isbn[9] * 10;

		result = sum % 11 == 0;
	}
	return result;
}

function isValidISBN13(isbn) {
	var result = false;

	if ( !isNaN( isbn ) ) { // isNaN - is Not a Number, !isNaN - is a number
		var index = 0;
		var sum = 0;

    //result = (isbn[0] * 1 + isbn[1] * 3 + isbn[2] * 1 + isbn[3] * 3 + ... + isbn[12] * 1) mod 10 == 0
		for ( var i = 0; i < isbn.length; i++ ) {
			sum += isbn[i] * (isOddNumber(index++) ? 3 : 1 );
		}
		result = sum % 10 == 0;
	}
	return result;
}

function isOddNumber (value) {
	return value % 2 != 0;
}

//function readURL(input) {
//    if (input.files && input.files[0]) {
//        var reader = new FileReader();
//
//        reader.onload = function (e) {
//            $('#imgPerfil').attr('src', e.target.result);
//        }
//
//        reader.readAsDataURL(input.files[0]);
//        alert("imatge seleccionada canviada")
//    }
//}














