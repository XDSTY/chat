function ajaxPOST(url, data, func){
    $.ajax({
        method: 'POST',
        cache: false,
        url:  url + '?timestamp=' + $.now(),
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (data) {
            if(data.code === 0){
                //执行传入的函数
                if(func){func();}
            }else if(data.code === -100 || data.code === -200){
                layer.msg(data.msg);
            }else{
                layer.msg('操作失败，请稍候重试');
            }
        }
    });
}


function ajaxPostWithData(url, data, func) {
    $.ajax({
        method: 'POST',
        cache: false,
        url:  url + '?timestamp=' + $.now(),
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (data) {
            if(data.code === 0){
                //执行传入的函数
                if(func){func(data);}
            }else if(data.code === -100 || data.code === -200){
                layer.msg(data.msg);
            }else{
                layer.msg('操作失败，请稍候重试');
            }
        }
    });
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}


Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "H+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
