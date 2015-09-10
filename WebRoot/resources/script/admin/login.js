window.onload = function() {

	var oTheme = getEle('.theme')[0];
	var oBody = getEle('body')[0];
	oTheme.onchange = function() {
 		
		var src = window.URL.createObjectURL(oTheme.files[0]);
		oBody.style.background = 'url(' + src + ') no-repeat';
		oBody.style.backgroundSize = 'cover';

	}

	var oCheck = getEle("#check");
	oCheck.onclick = function() {
        var oToggle = getChild(oCheck,1);

        if( oToggle.className.indexOf('fa-square-o') != -1 )
        	 oToggle.className = oToggle.className.replace(/fa-square-o/g,'fa-check-square-o');
        else
        	oToggle.className = oToggle.className.replace(/fa-check-square-o/g,'fa-square-o');
	}

	var oUser = getEle(".user")[0];
	var oPassword = getEle(".password")[0];
	var oUser_close = getEle('#user_close');
	var oPassword_close = getEle('#password_close');

	oUser.oninput = function() {
		if( this.value.length == 0) {
			oUser_close.style.display = 'none';
		} else {
			oUser_close.style.display = 'block';
		}
	}

	oPassword.oninput = function() {
		if( this.value.length == 0) {
			oPassword_close.style.display = 'none';
		} else {
			oPassword_close.style.display = 'block';
		}
	}

	oUser_close.onclick = function() {
		oUser.value = '';
		oUser_close.style.display = 'none';
	}

	oPassword_close.onclick = function() {
		oPassword.value = '';
		oPassword_close.style.display = 'none';
	}
}

function getEle(name,oParent) {

	if ( name.substr(0,1) == '#' )
		return document.getElementById(name.substr(1));

	if ( name.substr(0,1) == '.' ) {
		
		if( oParent == undefined ) {
			return document.getElementsByClassName(name.substr(1));
		}
		else {
			return oParent.getElementsByClassName(name.substr(1));
		}
	}
	else
		return document.getElementsByTagName(name);
}

function getChild(elem,index) {

	var elem_child = elem.childNodes;
	var childs = new Array();

	for (var i=0;i<elem_child.length;i++) {
		if( elem_child[i].nodeType == 1 )
			childs.push(elem_child[i]); 
	}

	return childs[index-1];
}