window.onload = function() {
  // 搜索框事件
  var $goods = $('#goods');
    var $close_icon = $('#close_icon');
    var $search_icon = $('#search_icon');

    $goods[0].onfocus = function () {
        $(this).css('text-align','left');
        $search_icon.css('left','3em');
    }

    $goods[0].oninput = function() {

      if(this.value.length == 0 ) {
        $close_icon.hide(); 
      } else {
        $close_icon.show();
      }
    }

    $close_icon[0].onclick = function() {
      $goods[0].value = '';
      $(this).hide();
    }

  // 筛选区的显示与隐藏
  var $filter = $('#filter');
  var $screen = $('#screen');
  var $slide = $('#slide'); 
  var $filter_btn = $('#filter_btn');

  function showFilter() {
      document.documentElement.style.overflow='hidden';
      $screen.show();
      $filter.css('right','0');
  }

  function hideFilter() {
      document.documentElement.style.overflow='scroll';
      $screen.hide();
      $filter.css('right','-40%');
  }
  
  // 点击事件
  $filter_btn.click(function(){

    if( $filter.css('right') !== '0px' ){

      showFilter();

    } else {

      hideFilter();

    }
      
  });
   
  // 滑动事件
    // setup event capturing
    var events = {

      handleEvent: function(event) {

          switch (event.type) {
              case 'touchstart': this.start(event); break;
              case 'touchmove': this.move(event); break;
              case 'resize': break;
          }

          event.stopPropagation();

      },
      start: function(event) {

          var touches = event.touches[0];

          // measure start values
          start = {

            // get initial touch coords
            x: touches.pageX,
            y: touches.pageY,

          };

          // used for testing first move event
          isScrolling = undefined;

          // reset delta and end measurements
          delta = {};

          // attach touchmove and touchend listeners
          $slide[0].addEventListener('touchmove', this, false);
          $screen[0].addEventListener('touchmove', this, false);

      },
      move: function(event) {

          if( event.target == $screen[0] ) window.event.preventDefault();

          // ensure swiping with one touch and not pinching
          if ( event.touches.length > 1 || event.scale && event.scale !== 1) return;

          var touches = event.touches[0];

          // measure change in x and y
          delta = {
            x: touches.pageX - start.x,
            y: touches.pageY - start.y
          }

          // determine if scrolling test has run - one time test
          if ( typeof isScrolling == 'undefined') {
              isScrolling = !!( isScrolling || Math.abs(delta.x) < Math.abs(delta.y) );
          }

          // if user is not trying to scroll vertically
          if (!isScrolling) {

                  if( delta.x > 20 ) {          // 右滑

                      hideFilter();

                    } else if ( delta.x < -20 ) {    // 左滑
                      
                      showFilter();

                    } //end if
    
          }// end if

      }

      }

      $slide[0].addEventListener('touchstart', events , false);
      $screen[0].addEventListener('touchstart',  events , false);


  // 设置筛选按钮被点击的背景颜色
  var $td1s = $('.filter table tr td[title=td1]');
  $td1s.click(function(){
    for(var i=0;i<$td1s.length;i++){
      $($td1s[i]).removeClass('active');
    }
    $(this).addClass('active');
  })


  //设置轮播图的高度
  var oMySwipe = document.getElementById('mySwipe');
  var oSWidth = oMySwipe.offsetWidth;
  oMySwipe.style.height = oSWidth * .4 +'px';
  
  //检测设备是横屏还是竖屏
  window.addEventListener('orientationchange',function(){
  	  //设置轮播图的高度比例
	  location.reload();
  },false);

  //设置水果图片的宽度和高度为1：1
  var $fruitImg = $('.fruitImg');
  $fruitImg.each(function(){
    $(this).height($(this).width());
  })

  var elem = document.getElementById('mySwipe');
  var element = elem.children[0];
  var  slides = element.children;
  var  length = slides.length;
	
  var num=0;      //小圆点的序号
  var oControl = document.getElementsByClassName('control')[0];   //放置小圆点的区域
  var spans;     //存储小圆点的数组
  
  //添加小圆点
  for(var i=0;i<slides.length;i++){
	  var span = document.createElement('span');
	  span.setAttribute('data-index', num);
	  num++
	  oControl.appendChild(span);
  }
  
	window.mySwipe = Swipe(elem, {
	   startSlide: 0,
	   auto: 2000,
	   continuous: false,
	   disableScroll: true,
	   stopPropagation: true,      //停止事件传播
	   //callback: function(index, element) {},
	   //transitionEnd: function(index, element) {}
	});

  //弹出提示对话框
  var $cart = $('.cart');
  var $dialog1 = $('#dialog1')

  var lastClickTime = new Date().getTime();
  var delay = 1500 // 动画的延迟

  $cart.each(function(index,value){
    $(this).click(function(){

      if( new Date().getTime() - lastClickTime < delay ) return;
    
      lastClickTime = new Date().getTime();

      $dialog1.animate({top:'50%'},'fast');
      $dialog1.animate({top:'45%'},'fast');
      $dialog1.animate({top:'50%'},'fast');
      $dialog1.animate({top:'50%'},'slow');
      $dialog1.animate({top:'45%'},'fast');
      $dialog1.animate({top:'120%'},'fast');
      $dialog1.animate({left:'200%'});
      $dialog1.animate({top:'0'});
      $dialog1.animate({left:'50%'});

    });
  })
 
  function getById(idName){
        return element = document.getElementById(idName);
  }

}
