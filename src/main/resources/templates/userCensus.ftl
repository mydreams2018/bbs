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
    <script src="/js/echarts.min.js"></script>
    <script type="text/javascript">
        function changeCategory(obj) {
            window.location.replace("/user/listAndCensus?groupField="+obj);
        };
        Date.prototype.format = function(fmt) {
            var o = {
                "M+" : this.getMonth()+1,                 //月份
                "d+" : this.getDate(),                    //日
                "h+" : this.getHours(),                   //小时
                "m+" : this.getMinutes(),                 //分
                "s+" : this.getSeconds(),                 //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S"  : this.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt)) {
                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            }
            for(var k in o) {
                if(new RegExp("("+ k +")").test(fmt)){
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                }
            }
            return fmt;
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
        <div class="col-md btn-group">
            <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                统计人数
            </button>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="javascript:changeCategory('origin_from')">类型</a>
                <a class="dropdown-item" href="javascript:changeCategory('register_year')">时间</a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md">
            <div id="echartsUser"  style="height:390px">

            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <select class="nav-item" id="select-type" onchange="selectChange(this)">
                            <option value="">所有类型</option>
                        </select>
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

    <div class="row pading30 justify-content-center">
        <div class="col-md-6">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" id="page-top" data-crt="1" href="javascript:pageChange('page-top')">首页</a></li>
                    <li class="page-item"><a class="page-link" id="page-last" href="javascript:pageChange('page-last')">上一页</a></li>
                    <li class="page-item"><a class="page-link" id="page-next" href="javascript:pageChange('page-next')">下一页</a></li>
                    <li class="page-item"><a class="page-link" id="page-end"  href="javascript:pageChange('page-end')">尾页</a></li>
                    <li class="page-item"><span id="current-page" class="page-link disabled"></span></li>
                    <li class="page-item"><span id="total-page" class="page-link disabled"></span></li>
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
        <p>粤ICP备19055569号</p>
    </div>
</footer>


<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('echartsUser'));
    option = {
        title : {
            text: '用户分类统计',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series : [
            {
                name: '用户统计',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    <#if categoryTotal??>
                        <#list categoryTotal as items>
                            {value:${(items.number)!0}, name:'${(items.categoryName)!无}'},
                        </#list>
                    </#if>
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    var category = "";
    var searchKeyword = "";

    function selectChange(obj) {
        category = $(obj).val();
        sendAjax();
    };

    function searchClick() {
        searchKeyword = $("#searchData").val();
        sendAjax();
    };

    $(document).ready(function(){
        myChart.setOption(option);

        $.ajax({
            url: "/user/categoryNames",
            type: "post",
            dataType: "json",
            success: function (data){
                if(data){
                    for(x = 0 ; x < data.length; x++){
                        var option = document.createElement("option");
                        option.text =  data[x];
                        option.value = data[x];
                        document.getElementById("select-type").appendChild(option);
                    }
                }
            }
        });

        sendAjax();
    });

    function sendAjax() {
        $.ajax({
            url: "/user/list",
            type: "post",
            dataType: "json",
            data:{
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

    function pageChange(obj) {
        var currentPage = $("#"+obj).attr("data-crt");
        if(currentPage){
            $.ajax({
                url: "/user/list",
                type: "post",
                dataType: "json",
                data: {"currentPage":currentPage,
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

    function getCurrentDatas(data) {
        if(data.datas.length > 0){
            var datas = data.datas;
            for (x = 0; x < datas.length; x++){
                var str = '<a class="dropdown-item" id="data-a-colne" href="#" data-toggle="tooltip" data-placement="top" title=""></a>';
                var posts = $(str);
                posts.prop("href", "/user/permissions?account="+datas[x].account);
                posts.text(datas[x].account + ':'+ new Date(datas[x].registerTime).format("yyyy-MM-dd"));
                posts.prop("title",datas[x].account);
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
</body>
</html>