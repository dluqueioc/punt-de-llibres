$(document).ready(function () {
    $("#selArxiu").change(function () {
        readURL(this);
    });
    $("#formAfegirLlibre").submit(function (event) {
        // validar(event);
    });
});

function validar(event) {
    if (!esTitolValid() || !esValidISBN() ||
        !esValidAutor() || !esValidEditorial() ||
        !esValidGenere() || !esValidEstil() ||
        !esValidIdioma() || !esValidEstatConserv() ||
        !esValidEdicio()
        // || !esValidArxiu()
    ) {
        event.preventDefault();
    }
}

function esTitolValid() {
    var titol = $('#titol').val();
    if (titol === "") {
        alert("El camp títol no pot estar buit");
        return false;
    }
    return true;
}

function esValidISBN() {
    var ISBN = $('#ISBN').val();
    if (ISBN === "") {
        alert("El camp ISBN no pot estar buit");
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
    var editorial = $('#editorial').val();
    if (editorial === "") {
        alert("El camp editorial no pot estar buit");
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

function esValidEstil() {
    if ($('#estil').find(":selected").text() == "Estil") {
        alert("Has d'escollir un estil");
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
    if ($('#estatConserv').find(":selected").text() == "Estat de conservació") {
        alert("Has d'escollir un estat de conservació");
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

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imgPerfil').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
        alert("imatge seleccionada canviada")
    }
}














