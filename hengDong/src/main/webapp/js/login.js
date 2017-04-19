function userlogin()
{
	var username = $("#username").val();
	var password = $("#password").val();
	$.post("./LoginController/userLogin.do",{
		username : username,
		password : password,
	});
}

function checkNameNotNull()
{
	var username = $("#username").val();
	if (null == username || '' == username || undefined == username)
	{
		$("#userNameEmpty").css("display","");
		return false;
	}
	$("#userNameEmpty").css("display","none");
	return true;
}

function checkPasswordNotNull()
{
	var password = $("#password").val();
	if (null == password || '' == password || undefined == password)
	{
		$("#userPwEmpty").css("display","");
		return false;
	}
	$("#userPwEmpty").css("display","none");
	return true;
	
}

function checkUserName()
{
	var username = $("#username").val();
	if (null == username || '' == username || undefined == username)
	{
		$("#userNameEmpty").css("display","");
	}
	else
	{
		$("#userNameEmpty").css("display","none");
	}
}

function checkPassword()
{
	var password = $("#password").val();
	if (null == password || '' == password || undefined == password)
	{
		$("#userPwEmpty").css("display","");
	}
	else
	{
		$("#userPwEmpty").css("display","none");
	}
}


function loginSubmit()
{
	var isUserNameEmpty = checkNameNotNull();
	var isPwEmpty = checkPasswordNotNull();
	var username = $("#username").val();
	var password = $("#password").val();
	if(!isUserNameEmpty || !isPwEmpty)
	{
		return false;
	}
	else
	{
		$.ajax({  
	        url : './LoginController/isNameNotMatchPw.do',  
	        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
	        type : "POST",  
	        data : {
	        	userName : username,
				password : password
	        },
	        success : function(data) {  


				var checkResult = data.checkResult;
				if (1 == checkResult)
				{
					$("#nameNotMatchPw").css("display","none");
					var nextUrl = "./LoginController/userLogin.do";  
					var theForm = $("#loginFormId");
					theForm.attr("action", nextUrl);  
					theForm.attr("onsubmit", '');  
					theForm.submit();  
					
				}
				else if (0 == checkResult)
				{
					isPwCorrect = false;
					$("#nameNotMatchPw").css("display","");
					$("#nameNotMatchPw").html("用户未启用");
				}
				else if (2 == checkResult)
				{
					isPwCorrect = false;
					$("#nameNotMatchPw").css("display","");
					$("#nameNotMatchPw").html("用户不存在")
				}
				else
				{
					isPwCorrect = false;
					$("#nameNotMatchPw").css("display","");
					$("#nameNotMatchPw").html("用户名密码不匹配");
				}
	        }  
	    });  
		
	}
	
	
}