$(document).ready(function () {
    const elems = document.querySelectorAll('.modal');
    const [modal, ...rest] = M.Modal.init(elems, { dismissible: true });

    $('#selFoto').change(function () {
        readURL(this);
    });

    $('#save-button').click(function (event) {
        if (!validar()) return;

        $('form').submit();
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
