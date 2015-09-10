$(window).load( function() {
	var $editPassword = $('#editPassword');
	var $passwordBox = $('#passwordBox');
	var $surePasswordEdit = $('#surePasswordEdit');
	$editPassword.click( function() {
		$passwordBox.show();
	});
	$surePasswordEdit.click( function() {
		$passwordBox.hide();
	});

	// 密码检索框
	var $password = $('#password');
	var $password1 = $('#password1');
	var $password2 = $('#password2');
	var $password3 = $('#password3');
	$password.attr('disabled',true);
	$password1.bind('blur',function() {

		if ( $password1.val() !== $password.val() && $password1.val()!== '' ) {
			alert("原密码输入错误！");
			$password1.val('');
		}
	});

	$password2.bind('focus',function() {
		if( $password1.val() == '' ) {
			alert('请先输入原密码');
			$password1.focus();
		}
	});
	$password3.bind('focus',function() {
		if( $password1.val() == '' ) {
			alert('请先输入原密码');
			$password1.focus();
		}
		else if( $password2.val() == '' ) {
			alert('请先新密码');
			$password2.focus();
		}
	})
	$password3.bind('blur',function() {

		if ( $password3.val() !== $password2.val() ) {
			alert("密码输入不一致，请重新输入！");
			$password3.val('');
		}
	});

});