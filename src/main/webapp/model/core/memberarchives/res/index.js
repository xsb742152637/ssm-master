layui.use(['element','upload','layer','form'], function(){
    let element = layui.element, upload = layui.upload, form = layui.form, layer = layui.layer;

    let canUpdate = true;
    //是否有编辑权限
    $.ajax({
        url: "/core/guide/canUpdate.do",
        dataType: 'text',
        type: "POST",
        async: false,
        success: function (rs) {
            if(!Boolean.parse(rs)){
                canUpdate = false;
                $('button,.update-photo').remove();
            }
        }
    });

    //执行实例
    var uploadInst = upload.render({
        elem: '#test1' //绑定元素
        ,url: '/file/save' //上传接口
        ,exts: "jpg|jpeg|bmp"
        ,done: function(res){
            //上传完毕回调
            console.log("执行上传请求后的回调");
            console.log(res);
            layer.load("正在更新……");
            $.ajax({
                url: "/core/memberArchives/saveMain.do",
                dataType: 'json',
                type: "POST",
                data: {memberId: memberId,photoUrl: res[0].relativePath},
                success: function (rs) {
                    layer.close();

                    $('.member-photo').empty().append('<img src="/core/memberArchives/getPhoto.do?memberId='+ memberId +'">');
                },
                error: function (jqXHR) {
                    layer.close();
                    console.log(jqXHR);
                }
            });
        }
    });
    // $('.member-photo').empty().append('');
});