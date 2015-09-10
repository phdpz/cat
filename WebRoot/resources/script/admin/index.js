$(window).load( function() {

	// 大屏幕下侧边导航栏
	var $As = $('#nav_bar ul li a');

	$As.each( function() {

		$(this).click( function() {

			$As.each( function(index,value) {
				$($As[index]).removeClass('active');
			}); 

			$(this).addClass('active');

		});  // click

	}); // each


	// 小屏幕的侧边导航栏
	var $Nav_bar = $('#nav_bar');
	var $Toggle_btn = $('#toggle_btn');

	$Toggle_btn.click( function() {

		if( $Nav_bar.css('left') !== '0px') {

			$Nav_bar.css({'left':'0px'});
			$(this).addClass('add');

		} else {

			$Nav_bar.css({ 'left' : '-' + ($Nav_bar.css('width')) });
			$(this).removeClass('add');

		}
		
	}); // click

});  // load

