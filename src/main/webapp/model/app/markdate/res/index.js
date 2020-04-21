layui.use(['month','layer'], function(){
    let month = layui.month, layer = layui.layer;
    //执行实例
    var uploadInst = month.render({
        elem: '#test1', //绑定元素
        toolbar: '#aaa',
        loadSuccess: function(e){
            console.log("加载完成");
            console.log(e);
        },
        click: function(e) { //节点选中状态改变事件监听，全选框有自己的监听事件
            console.log("点击单元格1");
            console.log(e);
        }
    });
    // $('.member-photo').empty().append('');
});