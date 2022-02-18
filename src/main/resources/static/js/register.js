$("#username").onkeyup(function(){
    $("#username").validate({
        rules: {
            username:{
                required:true,
                minlength:2,
                maxlength:10
            }
        },
        messages:{
            username:{
                required: "请输入用户名",
                minlength: "用户名至少由两个字母组成",
                maxlength: "用户名不能超过十个英文字母"
            }
        }
    })
})

$("#password").onkeyup(function(){
    $("#password").validate({
        rules: {
            password:{
                required:true,
                minlength:8,
                maxlength:20
            }
        },
        messages:{
            password:{
                required: "请输入密码",
                minlength: "密码需要八位以上有效字符",
                maxlength: "密码不能超过二十位有效字符"
            }
        }
    })
})

$("#validatePassword").onkeyup(function(){
    $("#validatePassword").validate({
        rules: {
            validatePassword:{
                required:true,
                equalTo: "#password"
            }
        },
        messages:{
            validatePassword:{
                required: "请再次确认密码",
                equalTo: "二次输入密码不同"
            }
        }
    })
})

$("#email").onkeyup(function(){
    $("#email").validate({
        rules: {
            email:{
                required:true,
                email: true
            }
        },
        messages:{
            email:{
                required: "请输入邮箱",
                email:"请输入正确邮箱号"
            }
        }
    })
})

$("#phone").onkeyup(function(){
    $("#phone").validate({
        rules: {
            phone:{
                required:true,
                minlength:11,
                maxlength:13,
                digits:true
            }
        },
        messages:{
            phone:{
                required: "请输入手机号",
                minlength:"请输入正确手机号",
                maxlength:"请输入正确手机号",
                digits:"请输入正确手机号"
            }
        }
    })
})

function sendInfo() {
    var password = $.trim(randomString(6));
    var email = $.trim($("#email").val());
    console.log(password);
    if (email == "") {
        alert("您输入的邮箱不完整..");
        return false;
    }
}

function randomString(length) {
    var str = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var result = '';
    for (var i = length; i > 0; --i)
        result += str[Math.floor(Math.random() * str.length)];
    resetTime();
    return result;
}

function resetTime(){
    var second = 60;
    var timer = null;
    timer = setInterval(function(){
        second -= 1;
        if(second >0){
            $("#validateEmail").attr('disabled',true);
            $("#validateEmail").text(second + "秒后获取验证码");
        }else{
            clearInterval(timer);
            $("#validateEmail").attr('disabled',false);
            $("#validateEmail").text("发送短信验证码");
        }
    },1000);
}

function register(){
    const name = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const verifyCode = document.getElementById("validateCode").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    var ajax = new XMLHttpRequest();
    ajax.open("post","/user/register");
    ajax.setRequestHeader("Content-type","application/json;charset = utf-8")
    ajax.send(JSON.stringify(name),JSON.stringify(password),JSON.stringify((verifyCode)),JSON.stringify(email),JSON.stringify(phone));
    ajax.onreadystatechange = function (){
        if(ajax.readyState == 4 && ajax.status == 200){
            if(ajax.responseText.length>=3){
                window.location.href="login2.html";
            }
            else{
                alert("注册失败");
            }
        }
        else{
            alert("注册失败")
        }
    }
}



