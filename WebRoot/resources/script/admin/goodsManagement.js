$(window).load(function(){
	var $page_nav_a = $('.page_nav a');
	$page_nav_a.each(function(){
		$(this).click(function(){
			for(var i=0;i<$page_nav_a.length;i++){
				$($page_nav_a[i]).removeClass('page_nav_active');
			}
			$(this).addClass('page_nav_active');
		})
	})

	// 弹出修改对话框
	var $dialog_edit = $('.dialog_edit');
	var $goods_dialog = $('#goods_dialog');
	var $goods_dialog_body = $('#goods_dialog_body');
	var $goods_dialog_footer_close = $('#goods_dialog_footer_close');
	var $goods_dialog_footer_sure = $('#goods_dialog_footer_sure')

	// 阻止弹出框滚动时间冒泡
	$.fn.extend({  
	    "preventScroll":function(){  

	            var _this = this[0];  
	            if(navigator.userAgent.indexOf('Firefox') >= 0){   //firefox  
	                _this.addEventListener('DOMMouseScroll',function(e){  
	                    _this.scrollTop += e.detail > 0 ? 60 : -60;     
	                    e.preventDefault();  
	                },false);   
	            }else{ 
	                _this.onmousewheel = function(e){     
	                    e = e || window.event;     
	                    _this.scrollTop += e.wheelDelta > 0 ? -60 : 60;     
	                    return false;  
	                };  
	            }    
	    }  
	});  
	$goods_dialog_body.preventScroll();  


	$dialog_edit.each(function(){
		$(this).click(function(){
			$goods_dialog.show();
		})
	})

	// 添加水果弹出框
	var $add_fruit = $('#add_fruit');
	$add_fruit.click(function() {
		$goods_dialog.show();
	})

	// 水果添加弹出框的图片上传
	var $img_file = $('#img_file');
	var $img_area  = $('#img_area');
	var $img_area_img = $($('#img_area img')[0]);
	/*显示上传图片*/
		$img_file.change(function(){

		   /*检测是否为图片文件*/
		   var ext = ['.gif', '.jpg', '.jpeg', '.png'];
		    
		    var str = document.getElementById('img_file').value;
		    
		    for(var i=0;i<ext.length;i++){
		    	if(str.indexOf(ext[i])!=-1 ){
		    		var flag = 1;
		    		break;
		    	}
		    }
		    
		    if(flag!=1){                   //如果检测的文件不为图片
		    	alert("请选择图片文件");
		    	$img_file.val('');
		    	$img_area.hide();
		    }
		    
		    /*设置图片路径*/
			var objUrl = getObjectURL(this.files[0]) ;
			
			if (objUrl) {
				$img_area_img.attr("src", objUrl) ;
				$img_area.show();
			}
		}) ;

		//建立一個可存取到該file的url
		function getObjectURL(file) {
			var url = null ; 
			if (window.createObjectURL!=undefined) { // basic
				url = window.createObjectURL(file) ;
			} else if (window.URL!=undefined) { // mozilla(firefox)
				url = window.URL.createObjectURL(file) ;
			} else if (window.webkitURL!=undefined) { // webkit or chrome
				url = window.webkitURL.createObjectURL(file) ;
			}
			return url ;
		}

	// 关闭弹出框
	var $goods_dialog_header_close = $('#goods_dialog_header_close')
	$goods_dialog_header_close.click(function(){
		$goods_dialog.hide();
	})

	$goods_dialog_footer_close.click(function(){
		$goods_dialog.hide();
	})

	// 弹出框的拖拽功能
	var $goods_dialog_header = $('#goods_dialog_header');
	$goods_dialog.draggable({handle:$goods_dialog_header});

	// 分页跳转输入框
	var $page = $('#page');
	$page.bind('blur',function() {

		var maxNum = parseInt($(this).attr('max'));
		var minNum = parseInt($(this).attr('min'));

		if ( $(this).val() > maxNum ) {

			$(this).val( maxNum );
		} else if ( $(this).val() < minNum ) {
			$(this).val( minNum );
		} else {
			$(this).val();
		}

	})

	// 图片弹出框
	var $good_img = $('.good_img');
	var $img_dialog = $('#img_dialog');
	var $img_dialog_close = $('#img_dialog span');
	var img_dialog_img = $('#img_dialog img')[0];
	$good_img.each( function() {
		$(this).click( function() {
			var src = $(this).children().eq(0).attr('src')
			$(img_dialog_img ).attr('src',src );
			$img_dialog.fadeIn();
		});
	});

	$img_dialog_close.click( function() {
		$img_dialog.fadeOut();
	})

	
	// 对话框
	var $dele = $('.dele');
	var $confirm_dialog = $('.confirm_dialog')[0];
	var $confirm_dialog_header = $('.confirm_dialog .confirm_dialog_header')[0];

	$($confirm_dialog).draggable({handle:$($confirm_dialog_header)});

	var lastClickTime = new Date().getTime();
    var delay = 2000 // 动画的延迟

	$dele.each( function() {
		$(this).click( function() {

			if( new Date().getTime() - lastClickTime < delay ) return;
    
     		lastClickTime = new Date().getTime();

			// 弹出框弹出代码
			$($confirm_dialog).show();
			$($confirm_dialog).animate({'top':'50%'},'fast');
			$($confirm_dialog).animate({'top':'45%'},'fast');
			$($confirm_dialog).animate({'top':'50%'},'fast');

		});
	});

	// 关闭对话框
	var $dialog_close = $('#dialog_close');
	var $dialog_cancel = $('#dialog_cancel');
	var $dialog_sure = $('#dialog_sure');
	$dialog_close.click( function() {
		$($confirm_dialog).hide();
		$($confirm_dialog).animate({'top':'-20%'},'fast');
	});
	$dialog_cancel.click( function() {
		$($confirm_dialog).hide();
		$($confirm_dialog).animate({'top':'-20%'},'fast');
	});
	$dialog_sure.click( function() {
		$($confirm_dialog).hide();
		$($confirm_dialog).animate({'top':'-20%'},'fast');
	}); 

});