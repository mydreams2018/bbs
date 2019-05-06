$(function(){

    $.ajax({
        url: "/postsCategory/list",
        type: "post",
        data: {"state":1},
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

});

function selectChange(obj) {

}

function orderDatas(obj) {
    var category = $("#select-type").val();
    alert(category);
}