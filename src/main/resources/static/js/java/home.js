$(function(){

    $.ajax({
        url: "/postsCategory/list",
        type: "post",
        data: {"state":1},
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
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
        url: "/javaPosts/selectAll",
        type: "post",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (data) {
            getDatas(data);
        }
    });

    $.ajax({
        url: "/javaPosts/selectAll",
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
                        a.prop("href","http://www.w3school.com.cn");
                        a.prop("title",datas[x].postsName);
                        a.text(datas[x].postsName.substring(0,18));
                    }
                }
            }
        }
    });

});

function getDatas(data) {
    if(data.datas.length > 0){
        var a = $("#newPosts");
        var datas = data.datas;
        for (x = 0; x < datas.length; x++){
            if(x < 8){
                a =  a.next("a");
                a.prop("href","http://www.w3school.com.cn");
                a.prop("title",datas[x].postsName);
                a.text(datas[x].postsName.substring(0,18));

                $("#postsDatas").append($(a).clone());
                var userMessage =  $(a).clone();
                userMessage.prop("href","http://www.w3school.com.cn");
                userMessage.text(datas[x].account +': '+ new Date(datas[x].publishTime).format("yyyy-MM-dd hh:mm:ss"));
                $("#text-right").append(userMessage);
            }else {
                var posts = $("#data-a-colne").clone();
                var userMessage = $("#data-a-colne").clone();
                posts.prop("href", "http://www.w3school.com.cn");
                posts.prop("title", datas[x].postsName);
                posts.text(datas[x].postsName.substring(0, 18));
                userMessage.prop("href", "http://www.w3school.com.cn");
                userMessage.prop("title", datas[x].postsName);
                userMessage.text(datas[x].account + ': ' + new Date(datas[x].publishTime).format("yyyy-MM-dd hh:mm:ss"));
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

function pageChange(obj) {
    var currentPage = $("#"+obj).attr("data-crt");
    if(currentPage){
        $.ajax({
            url: "/javaPosts/selectAll",
            type: "post",
            dataType: "json",
            data: {"currentPage":currentPage},
            beforeSend: function () {
                $("#postsDatas").empty();
                $("#text-right").empty();
                $("#page-last").parent("li").removeClass("disabled");
                $("#page-next").parent("li").removeClass("disabled");
                $("#page-end").parent("li").removeClass("disabled");
                $("#page-top").parent("li").removeClass("disabled");
            },
            success: function (data) {
                if(data.datas.length > 0){
                    var datas = data.datas;
                    for (x = 0; x < datas.length; x++){
                        var posts = $("#data-a-colne").clone();
                        var userMessage = $("#data-a-colne").clone();
                        posts.prop("href", "http://www.w3school.com.cn");
                        posts.prop("title", datas[x].postsName);
                        posts.text(datas[x].postsName.substring(0, 18));
                        userMessage.prop("href", "http://www.w3school.com.cn");
                        userMessage.prop("title", datas[x].postsName);
                        userMessage.text(datas[x].account + ': ' + new Date(datas[x].publishTime).format("yyyy-MM-dd hh:mm:ss"));
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
        });
    }
};

function orderDatas(obj) {
    var category = $("#select-type").val();
    alert(category);
};


function selectChange(obj) {

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