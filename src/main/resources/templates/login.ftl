<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="鲲之大一锅炖不下">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Kun 鲲大</title>
    <link rel="stylesheet" href="/css/bootstarp/bootstrap.min.css">
    <link href="/css/album.css" rel="stylesheet">
    <script src="/js/jquery-3.4.0.min.js" ></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.min.js" ></script>
    <script type="text/javascript">
        $(function(){
            $("#join_as").bind("click",function () {
                window.location.replace("/register.html");
            });
        });
        function flushImg(obj) {
            obj.src = "/image?time="+Math.random();
        };

        function check(){
            var name = $("#inputAccount").val();
            var pd = $("#inputPassword").val();
            var code = $("#inputImageCode").val();
            if(name.length < 6 || pd.length < 6 || code.length < 6 ){
                alert("用户-密码长度不能小于6");
                return ;
            }
            ajaxSend();
        };
        function ajaxSend() {
            var form = $("#sign_in");
            $.ajax({
                url: form.attr("action"),
                type: form.attr("method"),
                data: form.serialize(),
                dataType: "json",
                success: function (data) {
                    if(data.result){
                        window.location.replace(data.path);
                    }else{
                        alert(data.message);
                    }
                }
            });
        };
    </script>
</head>
<body>

<header class="sticky-top">
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-md-7 py-4">
                    <h4 class="text-white">About</h4>
                    <p class="text-muted"> 鲲大在线是一个技术性论坛,设立的初衷是提供一个供程序员、技术爱好者、教师和学生探讨技术的平台
                        如果侵犯了你的权益请email或QQ联系管理员以及相关作者，我们会在最短的时间内进行处理。同时论坛提醒所有引用他人资源时注明原作者与来源</p>
                </div>
                <div class="col-sm-4 offset-md-1 py-4">
                    <h4 class="text-white">Contact</h4>
                    <ul class="list-unstyled">
                        <li><a href="/register.html" class="text-white">注册</a></li>
                        <li><a href="/index" class="text-white">登录</a></li>
                        <li><a href="/clearAll" class="text-white">登出</a></li>
                        <li><a href="/address.html" class="text-white">联系我们</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="/self/list" class="navbar-brand d-flex align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-2"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path><circle cx="12" cy="13" r="4"></circle></svg>
                <strong>个人中心</strong>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>
</header>

<div class="row justify-content-center">
    <div class="col-md-3">
        <form action="/defaultLogin" method="post" id="sign_in">
            <div class="form-group">
                <label for="inputAccount">Account</label>
                <input type="text" id="inputAccount" name="username" class="form-control" placeholder="Account" required autofocus>
            </div>

            <div class="form-group">
                <label for="inputPassword">Password</label>
                <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
            </div>

            <div class="form-group">
                <label for="inputImageCode">Code</label>
                <input type="text" id="inputImageCode" name="code" class="form-control" placeholder="Code" required>
            </div>

            <div class="form-group">
                <img src="/image" onclick="flushImg(this)" class="img-fluid">
            </div>

            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" name="remember-me"> Remember me
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="button" onclick="check()">Sign in</button>
            <button class="btn btn-lg btn-primary btn-block" type="button" id="join_as">Join as</button>
            <p class="mt-5 mb-3 text-muted text-center"><a href="/home.html">&copy; 首页</a></p>
        </form>
    </div>
</div>

</body>
</html>