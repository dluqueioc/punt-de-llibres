$(document).ready(function() {
	$('.fixed-action-btn').floatingActionButton();
	$(".btn-floating").on("click", function(event) {
		//event.preventDefault();

		console.log('here');
		$(this).toggleClass("amagar");
		$(this).siblings().toggleClass("amagar");
		alert("Has demanat aquest llibre per intercanviar");

	});

});

//        var data = {
//            
//        }
//        $.ajax({
//            type: "POST",
//            url: '',
//            data: JSON.stringify(data),
//            contentType: 'application/json; charset=utf-8',
//            enctype: 'multipart/form-data',
//            dataType: 'json',
//        })
//            .then(() => {
//                var another = confirm('S\'ha solicitat l\'intercanvi per aquest llibre');
//                $(this).toggleClass("amagar");
//				  $(this).siblings().toggleClass("amagar");
//                if (! another) {
//                    location = '/';
//                }
//            })
//            .fail(() => alert('S\'ha produït un error'));