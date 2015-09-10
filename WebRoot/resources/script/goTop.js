function goTopFun() {
    
        var goTop = setInterval(scrollMove, 10);
        function scrollMove() {
            setScrollTop(getScrollTop() / 1.1);
            if (getScrollTop() < 1) clearInterval(goTop);
        }
}

window.onscroll = function () { 
    var bubuko_scrollheight = 200;     //页面离开顶部的距离，超过该距离才出现返回顶部图片
    var obj = document.getElementById('returnTop');
    getScrollTop() > bubuko_scrollheight ? obj.style.display = "inline-block" : obj.style.display = "none"; 
}

function getScrollTop() {
    return document.documentElement.scrollTop + document.body.scrollTop;    //解决多浏览器支持,chrome等浏览器下document.documentElement.scrollTop的值是0

}
function setScrollTop(value) {
    //解决多浏览器支持
    if (document.documentElement.scrollTop == 0) {
        document.body.scrollTop = value;
    }
    else {
        document.documentElement.scrollTop = value;       
    }
}
