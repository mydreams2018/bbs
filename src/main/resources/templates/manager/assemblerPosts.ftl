<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="汇编技术">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>编程技术</title>
    <link rel="stylesheet" href="/css/bootstarp/bootstrap.min.css" >
    <link href="/css/album.css" rel="stylesheet">
    <script src="/js/jquery-3.4.0.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.min.js" ></script>
    <script type="text/javascript">
        var orderField = "publish_time";
        var category = 0;
        var searchKeyword = "";
        $(function (){
            $.ajax({
                url: "/postsCategory/list",
                type: "post",
                data: {"state":2},
                dataType: "json",
                success: function (data) {
                    if(data.length > 0){
                        for(x = 0 ;x < data.length;x++){
                            var option = document.createElement("option");
                            option.text = data[x].categoryName;
                            option.value = data[x].id;
                            document.getElementById("select-type").appendChild(option);
                        }
                    }
                },
                error: function (data) {
                    alert(data.responseJSON.message);
                }
            });
            sendAjax();

        });

        function pageChange(obj) {
            var currentPage = $("#"+obj).attr("data-crt");
            if(currentPage){
                $.ajax({
                    url: "/assemblerPosts/selectAll",
                    type: "post",
                    dataType: "json",
                    data: {"currentPage":currentPage,
                        "orderField":orderField,
                        "category":category,
                        "searchKeyword":searchKeyword
                    },
                    beforeSend: function () {
                        $("#postsDatas").empty();
                        $("#page-last").parent("li").removeClass("disabled");
                        $("#page-next").parent("li").removeClass("disabled");
                        $("#page-end").parent("li").removeClass("disabled");
                        $("#page-top").parent("li").removeClass("disabled");
                    },
                    success: function (data) {
                        getCurrentDatas(data);
                    }
                });
            }
        };

        function selectChange(obj) {
            category = $(obj).val();
            sendAjax();
        };

        function searchClick() {
            searchKeyword = $("#searchData").val();
            sendAjax();
        };

        function orderDatas(obj) {
            orderField = obj;
            sendAjax();
        };

        function sendAjax() {
            $.ajax({
                url: "/assemblerPosts/selectAll",
                type: "post",
                dataType: "json",
                data: { "orderField":orderField,
                    "category":category,
                    "searchKeyword":searchKeyword
                },
                beforeSend: function () {
                    $("#postsDatas").empty();
                    $("#page-last").parent("li").removeClass("disabled");
                    $("#page-next").parent("li").removeClass("disabled");
                    $("#page-end").parent("li").removeClass("disabled");
                    $("#page-top").parent("li").removeClass("disabled");
                },
                success: function (data) {
                    getCurrentDatas(data);
                }
            });
        };

        function getCurrentDatas(data) {
            if(data.datas.length > 0){
                var datas = data.datas;
                for (x = 0; x < datas.length; x++){
                    var str = '<a class="dropdown-item" id="data-a-colne" href="#" data-toggle="tooltip" data-placement="top" title=""></a>';
                    var posts = $(str);
                    posts.prop("href", "/manager/editAssemblerDetails?id="+datas[x].id);
                    posts.prop("title", datas[x].postsName);
                    posts.text(datas[x].postsName.substring(0,18));
                    $("#postsDatas").append(posts);
                }
            }
            if(data.page){
                var page = data.page;
                $("#page-last").attr("data-crt",page.lastPage);
                $("#page-next").attr("data-crt",page.nextPage);
                $("#page-end").attr("data-crt",page.endPage);
                $("#current-page").text("当前页:"+page.currentPage);
                $("#total-page").text("总共页:"+page.totalPage);
                if(page.currentPage == page.lastPage){
                    $("#page-last").parent("li").addClass("disabled");
                }
                if(page.currentPage == page.nextPage){
                    $("#page-next").parent("li").addClass("disabled");
                }
                if(page.currentPage == page.endPage){
                    $("#page-end").parent("li").addClass("disabled");
                }
                if(page.currentPage == 1){
                    $("#page-top").parent("li").addClass("disabled");
                }
            }
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

<div class="container">

    <div class="row">
        <div class="col-md">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">

                        <select class="nav-item" id="select-type" onchange="selectChange(this)">
                            <option value="0">所有主题</option>
                        </select>

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                               aria-haspopup="true" aria-expanded="false">
                                排序
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="javascript:orderDatas('publish_time')">时间</a>
                                <a class="dropdown-item" href="javascript:orderDatas('reply_total')">回复量</a>
                            </div>
                        </li>
                    </ul>
                    <form class="form-inline my-2 my-lg-0">
                        <input class="form-control mr-sm-2" id="searchData" type="text" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success my-2 my-sm-0" onclick="searchClick()" type="button">Search</button>
                    </form>
                </div>
            </nav>
        </div>
    </div>

    <div class="row">
        <div class="col-md" id="postsDatas">
        </div>
    </div>

    <div class="row pading30">
        <div class="col-md">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item"><a class="page-link" id="page-top"  data-crt="1" href="javascript:pageChange('page-top')">首页</a></li>
                    <li class="page-item"><a class="page-link" id="page-last" data-crt="" href="javascript:pageChange('page-last')">上一页</a></li>
                    <li class="page-item"><a class="page-link" id="page-next" data-crt="" href="javascript:pageChange('page-next')">下一页</a></li>
                    <li class="page-item"><a class="page-link" id="page-end"  data-crt="" href="javascript:pageChange('page-end')">尾页</a></li>
                    <li class="page-item"><span id="current-page" class="page-link disabled">当前页:</span></li>
                    <li class="page-item"><span id="total-page" class="page-link disabled">总页面:</span></li>
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
        <p>这是一个由程序员编写的交流社区 &copy; DeathWater 开发的</p>
        <a href="http://www.beian.miit.gov.cn/" target="_blank">粤ICP备19055569号-1</a>
    </div>
</footer>

</body>
</html>