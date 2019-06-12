<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="编程技术">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>编程技术</title>
    <link rel="stylesheet" href="/css/bootstarp/bootstrap.min.css">
    <link href="/css/album.css" rel="stylesheet">
    <style type="text/css">
        .card-img-top{
            height: 330px;
        }
    </style>
    <link href="/summernote/summernote-bs4.css" rel="stylesheet">
    <link href="/css/zoomify.css" rel="stylesheet">
    <script src="/js/jquery-3.4.0.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.min.js" ></script>
    <script src="/summernote/summernote-bs4.js"></script>
    <script src="/js/zoomify.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 600
            });
            $("#zoomify img").zoomify();
            $("#zoomify img").addClass("img-fluid");
            $("#zoomify iframe").attr("width","100%");
            var current = ${(details.page.currentPage)!};
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
        var postsId = ${(posts.id)!};
        var sessionUser = "${(Session.KUN_CURRENT_NAME)!0}";
        function editPosts(obj){
            window.location.replace("/javaDetails/update?id="+obj);
        };

        function pageChange(obj) {
            var pag = $("#"+obj).attr("data-crt");
            window.location.replace("/javaPosts/javaDetails?id="+postsId+"&currentPage="+pag);
        };

        function flushImg(obj) {
            obj.src = "/image?time="+Math.random();
        };

        function sendPosts() {
            var markupStr = $('#summernote').summernote('code');
            var code = $("#inputImageCode").val();
            if(markupStr.length == 0){
                alert("贴子不能为空");
                return ;
            }
            if(markupStr.length > 3145720){
                alert("贴子信息过大,请重新编辑");
                return ;
            }
            $.ajax({
                url: "/javaDetails/save",
                type: "post",
                data: {
                    "postsId":postsId,
                    "detailData":markupStr,
                    "img-code":code
                },
                dataType: "json",
                success: function (data) {
                    if(data.result){
                        alert(data.message);
                        window.location.reload();
                    }else {
                        alert(data.message);
                    }
                }
            });
        };

        function sustain(obj) {
            if(sessionUser == 0){
                alert("请先登录");
                return ;
            }
            $.ajax({
                url: "/javaDetailsRecord/save",
                type: "post",
                data: {
                    "state":1,
                    "javaDetailsId":obj
                },
                dataType: "json",
                success: function (data) {
                    if(data.result){
                        alert(data.message);
                        window.location.reload();
                    }else {
                        alert(data.message);
                    }
                },
                error:function(data){
                    alert("请先登录");
                }
            });

        };
        function oppose(obj) {
            if(sessionUser == 0){
                alert("请先登录");
                return ;
            }
            $.ajax({
                url: "/javaDetailsRecord/save",
                type: "post",
                data: {
                    "state":0,
                    "javaDetailsId":obj
                },
                dataType: "json",
                success: function (data) {
                    if(data.result){
                        alert(data.message);
                        window.location.reload();
                    }else {
                        alert(data.message);
                    }
                },
                error:function(){
                    alert("请先登录");
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

<div class="row justify-content-center">
    <div class="col-md-8 text-center">
        <h1 style="color: #ff5479">${(posts.postsName)!}</h1>
    </div>
</div>

<#if (details.datas)??>

    <#if details.datas?size &gt; 0>
        <#list details.datas as data>
            <div class="row">
                    <div class="card col-md-3">
                            <img class="card-img-top img-fluid" style="padding: 10px" src="${(postsUsers[data_index].img)!}" alt="kun">
                            <div class="card-body">
                                <h5 class="card-title">${(postsUsers[data_index].account)!}</h5>
                                <h5 class="card-title">积分:${(postsUsers[data_index].accumulatePoints)!0}</h5>
                                <p class="card-text">${(postsUsers[data_index].description)!}</p>
                            </div>
                            <div class="card-footer">
                                <small class="text-muted">
                                    <img src="${(postsUsers[data_index].icon)!}" class="img-fluid" alt="">
                                    <#if (postsUsers[data_index].registerTime)??>
                                        ${(postsUsers[data_index].registerTime)?string("yyyy-MM-dd HH:mm:ss")}
                                    <#else>
                                        BUG用户
                                    </#if>
                                    &nbsp; &nbsp;
                                    <a href="/sendMessage/send?toAccount=${(postsUsers[data_index].account)!}">私信:</a>
                                </small>
                            </div>
                    </div>
                    <div class="card col-md-9">
                        <div class="card-body" id="zoomify">
                            ${(data.detailData)!}
                        </div>
                        <div class="card-footer text-right">
                            <small class="text-muted">
                                <a href="javascript:sustain(${(data.id)!})" class="card-link">
                                    <#if (records[data_index][0])?? >
                                        <#if records[data_index][0].state>
                                            顶:${(records[data_index][0].total)!0}
                                            <#assign sustains = (records[data_index][0].account)!'无'>
                                        <#else>
                                            顶:0
                                            <#assign sustains = '无'>
                                        </#if>
                                    <#else>
                                        顶:0
                                        <#assign sustains = '无'>
                                    </#if>
                                </a>
                                <a href="javascript:oppose(${(data.id)!})" class="card-link">
                                    <#if (records[data_index][0])?? >
                                        <#if records[data_index][0].state == false>
                                            踩:${(records[data_index][0].total)!0}
                                            <#assign opposes = (records[data_index][0].account)!'无'>
                                        <#else>
                                            踩:${(records[data_index][1].total)!0}
                                            <#assign opposes = (records[data_index][1].account)!'无'>
                                        </#if>
                                    <#else>
                                        踩:0
                                        <#assign opposes = '无'>
                                    </#if>
                                </a>

                                &nbsp; &nbsp; &nbsp;
                                发布时间:${(data.publishTime)?string("yyyy-MM-dd HH:mm:ss")}
                                &nbsp; &nbsp; &nbsp;
                                <#if (data.updateTime)??>
                                    最后修改时间:${(data.updateTime)?string("yyyy-MM-dd HH:mm:ss")}
                                </#if>
                                <#if (Session.KUN_CURRENT_NAME)??>
                                    <#if Session.KUN_CURRENT_NAME == data.account>
                                        <a href="javascript:editPosts(${(data.id)!})" class="card-link">修改内容</a>
                                    </#if>
                                </#if>

                                <a  data-toggle="collapse" href="#collapse${(data.id)!}" role="button" aria-expanded="false" aria-controls="collapse${(data.id)!}">
                                    &nbsp; &nbsp;查:
                                </a>
                                <div class="collapse" id="collapse${(data.id)!}">
                                    <div class="card card-body">
                                        顶:${(sustains)!'无'}
                                        踩:${(opposes)!'无'}
                                    </div>
                                </div>
                            </small>
                        </div>
                    </div>
            </div>
        </#list>
    <#else>

    </#if>
</#if>

<#if (Session.KUN_CURRENT_NAME)?? >
    <div class="row pading30 justify-content-end">
        <div class="col-md-9">
            <form id="send-java-posts" method="post" action="/javaDetails/save">
                <div class="form-group">
                    <label for="summernote">最大内容3m内:图片过大请使用外部链接</label>
                    <textarea class="form-control" id="summernote" name="detailData"></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="inputImageCode">Code</label>
                        <input type="text" id="inputImageCode" name="img-code" class="form-control" placeholder="img-code" required>
                    </div>
                    <div class="form-group col-md-4">
                        <img src="/image" onclick="flushImg(this)" alt="鲲" id="image_flush" style="height: 76px" class="img-fluid form-control">
                    </div>
                </div>

                <button type="button" class="btn btn-primary" onclick="sendPosts()">确定</button>
            </form>
        </div>
    </div>

<#else>
    <div class="p-3 mb-2 bg-info text-white text-center">
        登录后发贴: <a href="/index" class="text-white">登录</a>
    </div>
</#if>

<div class="row pading30 justify-content-end">
    <div class="col-md-6">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" id="page-top"  data-crt="1" href="javascript:pageChange('page-top')">首页</a></li>
                <li class="page-item"><a class="page-link" id="page-last" data-crt="${(details.page.lastPage)!}" href="javascript:pageChange('page-last')">上一页</a></li>
                <li class="page-item"><a class="page-link" id="page-next" data-crt="${(details.page.nextPage)!}" href="javascript:pageChange('page-next')">下一页</a></li>
                <li class="page-item"><a class="page-link" id="page-end"  data-crt="${(details.page.endPage)!}" href="javascript:pageChange('page-end')">尾页</a></li>
                <li class="page-item"><span id="current-page" class="page-link disabled">当前页:${(details.page.currentPage)!}</span></li>
                <li class="page-item"><span id="total-page" class="page-link disabled">总页面:${(details.page.totalPage)!}</span></li>
            </ul>
        </nav>
    </div>
</div>

</body>
</html>