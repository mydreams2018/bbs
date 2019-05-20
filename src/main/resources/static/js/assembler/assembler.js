var orderField = "publish_time";
var category = 0;
var searchKeyword = "";

$(function(){

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

    $.ajax({
        url: "/assemblerPosts/selectAll",
        type: "post",
        dataType: "json",
        success: function (data) {
            getDatas(data);
        }
    });

    $.ajax({
        url: "/assemblerPosts/selectAll",
        type: "post",
        dataType: "json",
        data: {"orderField":"reply_time_end","pageSize":8},
        success: function (data) {
            if(data.datas.length > 0){
                var a = $("#newReply");
                var datas = data.datas;
                for (x = 0; x < datas.length; x++){
                    if(x < 8){
                        a =  a.next("a");
                        a.prop("href","/assemblerPosts/details?id="+datas[x].id);
                        a.prop("title",datas[x].postsName);
                        a.text(datas[x].postsName.substring(0,18));
                    }
                }
            }
        }
    });

    $.ajax({
        url: "/assemblerPosts/selectAll",
        type: "post",
        dataType: "json",
        data: {"orderField":"reply_total","pageSize":8},
        success: function (data) {
            if(data.datas.length > 0){
                var a = $("#hotReply");
                var datas = data.datas;
                for (x = 0; x < datas.length; x++){
                    if(x < 8){
                        a =  a.next("a");
                        a.prop("href","/assemblerPosts/details?id="+datas[x].id);
                        a.prop("title",datas[x].postsName);
                        a.text(datas[x].postsName.substring(0,18));
                    }
                }
            }
        }
    });

});
//页面重载时
function getDatas(data) {
    if(data.datas.length > 0){
        var a = $("#newPosts");
        var datas = data.datas;
        for (x = 0; x < datas.length; x++){
            if(x < 8){
                a =  a.next("a");
                a.prop("href","/assemblerPosts/details?id="+datas[x].id);
                a.prop("title",datas[x].postsName);
                a.text(datas[x].postsName.substring(0,18));

                $("#postsDatas").append($(a).clone());
                var userMessage =  $(a).clone();
                userMessage.prop("href","/assembler/accountPosts.html?account="+datas[x].account);
                userMessage.text(datas[x].account +': '+ new Date(datas[x].publishTime).format("yyyy-MM-dd"));
                $("#text-right").append(userMessage);
            }else {
                var posts = $("#data-a-colne").clone();
                var userMessage = $("#data-a-colne").clone();
                posts.prop("href", "/assemblerPosts/details?id="+datas[x].id);
                posts.prop("title", datas[x].postsName);
                posts.text(datas[x].postsName.substring(0, 18));
                userMessage.prop("href", "/assembler/accountPosts.html?account="+datas[x].account);
                userMessage.prop("title", datas[x].postsName);
                userMessage.text(datas[x].account + ': ' + new Date(datas[x].publishTime).format("yyyy-MM-dd"));
                $("#postsDatas").append(posts);
                $("#text-right").append(userMessage);
            }
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

// to do

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
                $("#text-right").empty();
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

function orderDatas(obj) {
    orderField = obj;
    sendAjax();
};


function selectChange(obj) {
    category = $(obj).val();
    sendAjax();
};

function searchClick() {
    searchKeyword = $("#searchData").val();
    sendAjax();
}

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
            $("#text-right").empty();
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

function getCurrentDatas(data) {
    if(data.datas.length > 0){
        var datas = data.datas;
        for (x = 0; x < datas.length; x++){
            var posts = $("#data-a-colne").clone();
            var userMessage = $("#data-a-colne").clone();
            posts.prop("href", "/assemblerPosts/details?id="+datas[x].id);
            posts.prop("title", datas[x].postsName);
            posts.text(datas[x].postsName.substring(0, 18));
            userMessage.prop("href", "/assembler/accountPosts.html?account="+datas[x].account);
            userMessage.prop("title", datas[x].postsName);
            userMessage.text(datas[x].account + ': ' + new Date(datas[x].publishTime).format("yyyy-MM-dd"));
            $("#postsDatas").append(posts);
            $("#text-right").append(userMessage);
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
}

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