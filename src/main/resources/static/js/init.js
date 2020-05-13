(function($){
  $(function(){
    $('.sidenav').sidenav();
    $('.parallax').parallax();
    $('.carousel').carousel({
    	fullWidth: true,
    	//dist: 0,
        //padding: 0,
        indicators: true,
        //duration: 100,
    });
    // $('.modal').modal();
    $(".dropdown-trigger").dropdown({constrainWidth: false });
	$('.collapsible').collapsible();
	$('.slider').slider({
		indicators: false
	});
  }); // end of document ready
})(jQuery); // end of jQuery name space
