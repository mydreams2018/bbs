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
        function jumpMessage(obj) {
            window.location.replace("/messageDetails/list?messageId="+obj);
        };
        function pageChange(obj) {
            var end = $("#page-end").attr("data-crt");
            if(obj > end ){
                alert("当前页不能大过总页:");
                window.location.replace("/sendMessage/to?currentPage="+'1');
            }
            window.location.replace("/sendMessage/to?currentPage="+obj);
        };
        $(document).ready(function() {
            var current = ${(ms.page.currentPage)!0};
            var last =  $("#page-last").attr("data-crt");
            var next = $("#page-next").attr("data-crt");
            var end = $("#page-end").attr("data-crt");
            var top = $("#page-top").attr("data-crt");
            if(current == last){
                $("#page-last").parent("li").addClass("disabled");
            }
            if(current == next){
                $("#page-next").parent("li").addClass("disabled");
            }
            if(current == end){
                $("#page-end").parent("li").addClass("disabled");
            }
            if(top == current){
                $("#page-top").parent("li").addClass("disabled");
            }
        });
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

<#if ms.datas?size gt 0 >

<div class="container">
    <div class="card-group">

        <#list ms.datas as messages>

            <div class="card" onclick="jumpMessage(${(messages.id)!0})">
                <img src="${(messages.fromImg)!}" class="card-img-top img-fluid" alt="" style="height: 260px">
                <div class="card-body">
                    <h5 class="card-title">${(messages.fromAccount)!}</h5>
                    <p class="card-text">${(messages.message)!}</p>
                </div>
                <div class="card-footer">
                    <small class="text-muted">${(messages.sendTime)?string("yyyy-MM-dd HH:mm:ss")}</small>
                </div>
            </div>

        </#list>

    </div>
</div>

</#if>

<div class="container">
    <div class="row pading30">
        <div class="col-md">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item"><a class="page-link" id="page-top" data-crt="1" href="javascript:pageChange(${(ms.page.topPage)!0})">首页</a></li>
                    <li class="page-item"><a class="page-link" id="page-last" data-crt="${(ms.page.lastPage)!0}" href="javascript:pageChange(${(ms.page.lastPage)!0})">上一页</a></li>
                    <li class="page-item"><a class="page-link" id="page-next" data-crt="${(ms.page.nextPage)!0}" href="javascript:pageChange(${(ms.page.nextPage)!0})">下一页</a></li>
                    <li class="page-item"><a class="page-link" id="page-end"  data-crt="${(ms.page.endPage)!0}" href="javascript:pageChange(${(ms.page.endPage)!0})">尾页</a></li>
                    <li class="page-item"><span id="current-page" class="page-link disabled">当前页:${(ms.page.currentPage)!}</span></li>
                    <li class="page-item"><span id="total-page" class="page-link disabled">总页面:${(ms.page.totalPage)!}</span></li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<footer class="text-muted">
    <div class="container">
        <p class="float-right">
            <a href="#">Back to top</a>
        </p>
        <p>邮箱:mydreams2018@outlook.com</p>
        <a href="http://www.beian.miit.gov.cn/" target="_blank">粤ICP备19055569号-1</a>
    </div>
</footer>

</body>
</html>