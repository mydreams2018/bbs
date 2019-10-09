<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="编程技术">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>编程技术</title>
    <link rel="stylesheet" href="/css/bootstarp/bootstrap.min.css" >
    <link href="/css/album.css" rel="stylesheet">
    <link href="/summernote/summernote-bs4.css" rel="stylesheet">
    <script src="/js/jquery-3.4.0.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.min.js" ></script>
    <script src="/summernote/summernote-bs4.js" ></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 600
            });
        });
        function sendPosts() {
            var markupStr = $('#summernote').summernote('code');
            if (markupStr.length > 3145720){
                alert("贴子信息过大,请重新编辑");
                return ;
            }
            var form = $("#send-java-posts");
            $.ajax({
                url: form.attr("action"),
                type: form.attr("method"),
                data: form.serialize(),
                dataType: "json",
                success: function (data) {
                    if(data.result){
                        alert(data.message);
                        window.location.replace("/java.html");
                    }else {
                        alert(data.message);
                    }
                }
            });
        };
        function flushImg(obj) {
            obj.src = "/image?time="+Math.random();
        };
    </script>
</head>
<body>

<header class="fixed-top">
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

<div class="container pading30">
    <div class="row justify-content-center">
        <div class="col-md">
            <form id="send-java-posts" method="post" action="/javaPosts/save">
                <div class="form-row">
                    <div class="form-group col-md-8">
                        <label for="send-main">主题</label>
                        <input type="email" class="form-control" id="send-main" name="postsName">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="sendCategory">类型</label>
                        <select class="form-control" id="sendCategory" name="category">
                            <#if categorys??>
                                <#if categorys?size &gt; 0>
                                    <#list categorys as data>
                                        <option value="${(data.id)!}">${(data.categoryName)!}</option>
                                    </#list>
                                <#else>

                                </#if>
                            </#if>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="inputImageCode">Code</label>
                        <input type="text" id="inputImageCode" name="img-code" class="form-control" placeholder="img-code" required>
                    </div>

                    <div class="form-group col-md-8">
                        <img src="/image" onclick="flushImg(this)" alt="鲲" id="image_flush" style="height: 76px" class="img-fluid form-control">
                    </div>
                </div>

                <div class="form-group">
                    <label for="summernote">最大内容3m内:图片过大请使用外部链接</label>
                    <textarea class="form-control" id="summernote" name="detailData"></textarea>
                </div>
                <button type="button" class="btn btn-primary" onclick="sendPosts()">确定</button>
            </form>
        </div>
    </div>
</div>

<footer class="text-muted">
    <div class="container">
        <p class="float-right">
            <a href="#">Back to top</a>
        </p>
        <p>这是一个由程序员编写的交流社区 &copy; DeathWater 开发的</p>
        <p>粤ICP备19055569号</p>
    </div>
</footer>

</body>
</html>