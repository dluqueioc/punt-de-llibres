(function($){
  $(function(){
    $('.sidenav').sidenav();
    $('.parallax').parallax();
    $('.carousel').carousel(
        //dist: 0,
        //padding: 0,
        //fullWidth: true,
        //indicators: true,
        //duration: 100,
    );
    // $('.modal').modal();
    $(".dropdown-trigger").dropdown({constrainWidth: false });
	$('.collapsible').collapsible();
  }); // end of document ready
})(jQuery); // end of jQuery name space
