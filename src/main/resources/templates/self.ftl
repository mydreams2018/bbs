<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="编程技术">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>编程技术</title>
    <link rel="stylesheet" href="/css/bootstarp/bootstrap.min.css" >
    <link href="/css/album.css" rel="stylesheet">
    <script src="/js/jquery-3.4.0.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.min.js" ></script>
    <script type="text/javascript">
        function chedkSend() {
            var imgPath = $("#exampleFormControlFile1").prop("files");
            if(imgPath.length > 0){
                if(imgPath[0].size > 1048000){
                    alert("图片不能大于1M");
                    return ;
                };
                var data = new FormData(document.getElementById("imgForm"));
                $.ajax({
                    type: "post",
                    url: "/self/uploadImg",
                    contentType: false,
                    processData: false,
                    data: data,
                    dataType: "json",
                    success: function (data) {
                        if(data.result){
                            $("#user-img").attr("src",data.path);
                        }else{
                            alert(data.message);
                        }
                    }
                });
            }
        };
        function changeData() {
            var email = $("#email").val();
            var phone = $("#phone").val();
            var alias = $("#alias").val();
            var area = $("#exampleFormControlTextarea1").val();
            if(email.length==0 || phone.length ==0 || alias.length == 0){
                alert("信息不能为空");
                return;
            }
            if(area.length > 156){
                alert("个人简介长度不能超过156个字");
                return;
            }
            var reg = new RegExp("^[0-9]*$");
            if(!reg.test(phone)){
                alert("电话只能是数字");
                return ;
            }
            var form = $("#change-data");
            $.ajax({
                url: form.attr("action"),
                type: form.attr("method"),
                data: form.serialize(),
                dataType: "json",
                success: function (data) {
                    alert(data.message);
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
                    <p class="text-muted">编程技术是一个技术性论坛,设立的初衷是提供一个供程序员、技术爱好者、教师和学生探讨技术的平台
                        如果侵犯了你的权益请email或QQ联系管理员以及相关作者，我们会在最短的时间内进行处理。同时论坛提醒所有引用他人资源时注明原作者与来源</p>
                </div>
                <div class="col-sm-4 offset-md-1 py-4">
                    <h4 class="text-white">Contact</h4>
                    <ul class="list-unstyled">
                        <li><a href="/register.html" class="text-white">注册</a></li>
                        <li><a href="/index" class="text-white">登录</a></li>
                        <li><a href="/clearAll" class="text-white">登出</a></li>
                        <li><a href="/home.html" class="text-white">首页</a></li>
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

<div class="container" style="padding-top: 50px">

    <div class="row" style="padding-bottom: 20px">
        <div class="col-sm-4">
            <img id="user-img" src="${(userDetail.img)!}" class="img-fluid rounded-circle" style="height: 200px;width:100%">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-4">
            <div class="list-group" id="list-tab" role="tablist">
                <a class="list-group-item list-group-item-action active" id="list-home-list" data-toggle="list" href="#list-home" role="tab" aria-controls="home">个人头像</a>
                <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#list-profile" role="tab" aria-controls="profile">个人资料</a>
                <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#list-messages" role="tab" aria-controls="messages">个人发贴</a>
                <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings">个人跟贴</a>
                <a class="list-group-item list-group-item-action" id="list-message-list" data-toggle="list" href="#list-message" role="tab" aria-controls="messages">个人私信</a>
            </div>
        </div>
        <div class="col-sm-8">
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
                    <form method="post" id="imgForm" enctype="multipart/form-data" action="/self/uploadImg">
                        <div class="form-group">
                            <label for="exampleFormControlFile1">请选择要上传的图片不能大于1m</label>
                            <input type="file" class="form-control-file" name="imgPath" id="exampleFormControlFile1">
                            <button type="button" onclick="chedkSend()" class="btn btn-primary" style="margin-top: 10px">提交</button>
                        </div>
                    </form>
                </div>
                <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">
                    <form id="change-data" method="post" action="/self/changeData">
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="inputEmail4">邮箱</label>
                                <input type="email" class="form-control" id="email" value="${(userDetail.email)!}" name="email">
                            </div>
                            <div class="form-group col-md-4">
                                <label for="phone">电话</label>
                                <input type="text" class="form-control" id="phone" value="${(userDetail.phone)!}" name="phone">
                            </div>
                            <div class="form-group col-md-4">
                                <label for="alias">别名</label>
                                <input type="text" class="form-control" id="alias" value="${(userDetail.alias)!}" name="alias">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">个人简介</label>
                            <textarea class="form-control" id="exampleFormControlTextarea1" name="description" rows="3">${(userDetail.description)!}</textarea>
                        </div>
                        <button type="button" class="btn btn-primary" onclick="changeData()">确定修改</button>
                    </form>
                </div>

                <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">
                    <a class="btn btn-primary" href="/accountPosts.html?account=${(userDetail.account)!}" role="button">java跳转</a>
                    <a class="btn btn-primary" href="/assembler/accountPosts.html?account=${(userDetail.account)!}" role="button">汇编跳转</a>
                    <a class="btn btn-primary" href="/data/accountPosts.html?account=${(userDetail.account)!}" role="button">数据跳转</a>
                </div>

                <div class="tab-pane fade" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">
                    <a class="btn btn-primary" href="/accountReplyPosts.html?account=${(userDetail.account)!}" role="button">java跳转</a>
                    <a class="btn btn-primary" href="/assembler/accountReplyPosts.html?account=${(userDetail.account)!}" role="button">汇编跳转</a>
                    <a class="btn btn-primary" href="/data/accountReplyPosts.html?account=${(userDetail.account)!}" role="button">数据跳转</a>
                </div>

                <div class="tab-pane fade" id="list-message" role="tabpanel" aria-labelledby="list-message-list">
                    <a class="btn btn-primary" href="/sendMessage/from" role="button">我发送的</a>
                    <a class="btn btn-primary" href="/sendMessage/to" role="button">我接收的</a>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="text-muted">
    <div class="container">

        <div class="row">
            <div class="col-sm-4">
                <p>这是一个由程序员编写的交流社区 &copy; DeathWater 开发的</p>
                <a href="http://www.beian.miit.gov.cn/" target="_blank">粤ICP备19055569号-1</a>
            </div>
        </div>

    </div>
</footer>

</body>
</html>