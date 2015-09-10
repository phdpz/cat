window.onload = function() {

	var $lists = $('.list');

	$lists.each(function() {             //遍历每个列表

		// checkbox 选中与否的事件
		$(this).children().eq(0).children().eq(1).bind('click',function() {
			
			if ( $(this).hasClass('fa-circle-thin') ) {
				$(this).removeClass('fa-circle-thin');
				$(this).addClass('fa-check-circle-o');
				$(this).prev().attr('checked',true);
			}else {
				$(this).removeClass('fa-check-circle-o');
				$(this).addClass('fa-circle-thin');
					$(this).prev().attr('checked',false);
			}
		});

		// 修改数量下拉框弹出事件
		$(this).children().eq(3).bind('click',function() { 
			$(this).next().slideToggle('fast');
		});
	})


	// 全选按钮
	var $allselect = $('#allselect');
	$allselect.click( function() {

        if( $(this).hasClass('fa-circle-thin')  ) {

        	$(this).removeClass('fa-circle-thin');
			$(this).addClass('fa-check-circle-o');

			$('.list .col1 input').each(function(){
				$(this).attr('checked',true);
			})

			$('.list .col1 .select').each(function() {
				$(this).removeClass('fa-circle-thin');
				$(this).addClass('fa-check-circle-o');
			})

		}else {

			$(this).removeClass('fa-check-circle-o');
			$(this).addClass('fa-circle-thin');

			$('.list .col1 input').each(function(){
				$(this).attr('checked',false);
			})

			$('.list .col1 .select').each( function() {
				$(this).removeClass('fa-check-circle-o');
				$(this).addClass('fa-circle-thin');
			})

		}
	})

  //弹出提示对话框
  var $cart = $('.cart');
  var $dialog2 = $('#dialog2');
  var $sure = $('#sure');
  var $del = $('#del');

  var lastClickTime = new Date().getTime();
  var delay = 1500 // 动画的延迟

  $cart.each(function(index,value){
    $(this).click(function(){

      if( new Date().getTime() - lastClickTime < delay ) return;
    
      lastClickTime = new Date().getTime();

      $dialog2.animate({top:'50%'},'fast');
      $dialog2.animate({top:'45%'},'fast');
      $dialog2.animate({top:'50%'},'fast');
    })

  })

  $sure.click(function(){
  	$dialog2.animate({top:'45%'},'fast');
  	$dialog2.animate({top:'120%'},'fast');
	$dialog2.animate({left:'200%'});
	$dialog2.animate({top:'0'},'.001');
	$dialog2.animate({left:'50%'},'.001');
  })

  $del.click(function(){
  	$dialog2.animate({top:'45%'},'fast');
	$dialog2.animate({top:'120%'},'fast');
	$dialog2.animate({left:'200%'});
	$dialog2.animate({top:'0'},'.001');
	$dialog2.animate({left:'50%'},'.001');  	
  })

}
