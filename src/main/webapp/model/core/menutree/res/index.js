layui.use(['tree'], function(){
    var tree = layui.tree;

    $.ajax({
        url: "/core/menuTree/getMenuTree.do",
        dataType: 'json',
        type: "POST",
        success: function (data) {
            if (data != null && data.length > 0) {
                //渲染
                var inst1 = tree.render({
                    elem: '#my-tree',  //绑定元素
                    accordion: true,//开启手风琴模式
                    onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
                    data: data,
                    on: function(data) { //节点选中状态改变事件监听，全选框有自己的监听事件
                        console.log(data); //得到checkbox原始DOM对象
                    }
                });
            }

        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });

});