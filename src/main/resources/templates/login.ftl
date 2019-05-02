<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="鲲之大一锅炖不下">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Kun 鲲大</title>
    <link rel="stylesheet" href="/css/bootstarp/bootstrap.min.css" >
    <link rel="stylesheet" href="/css/floating-labels.css">
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

    <form class="form-signin" action="/defaultLogin" method="post" id="sign_in">
        <div class="text-center mb-4">
            <img class="mb-4" src="/favicon.ico" alt="鲲大" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal">鲲之大一锅炖不下</h1>
        </div>

  <#--      <div class="form-label-group">
            <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
            <label for="inputEmail">Email address</label>
        </div>-->

        <div class="form-label-group">
            <input type="text" id="inputAccount" name="username" class="form-control" placeholder="Account" required autofocus>
            <label for="inputAccount">Account</label>
        </div>

        <div class="form-label-group">
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
            <label for="inputPassword">Password</label>
        </div>

        <div class="form-label-group">
            <input type="text" id="inputImageCode" name="code" class="form-control" placeholder="Code" required>
            <label for="inputImageCode">Code</label>
        </div>

        <div class="form-label-group">
            <img src="/image" onclick="flushImg(this)" class="img-fluid">
        </div>

        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="check()">Sign in</button>
        <button class="btn btn-lg btn-primary btn-block" type="button" id="join_as">Join as</button>
        <p class="mt-5 mb-3 text-muted text-center"><a href="/home.html">&copy; 首页</a></p>
    </form>

</body>
</html>