function login(){
    const name = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    var ajax = new XMLHttpRequest();
    ajax.open("post","/user/login");
    ajax.setRequestHeader("Content-type","application/json;charset = utf-8")
    ajax.send(JSON.stringify(name),JSON.stringify(password));
    ajax.onreadystatechange = function (){
        if(ajax.readyState == 4 && ajax.status == 200){
            if(ajax.responseText.length>=3){
                window.location.href="index.html";
            }
            else{
                alert("登录失败");
            }
        }
        else{
            alert("登录失败")
        }
    }
}
