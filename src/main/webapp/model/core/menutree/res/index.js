var tree,layer;
layui.use(['tree','layer'], function(){
    tree = layui.tree;
    layer = layui.layer;
    var selData = null;//菜单树选中的信息

    layer.msg('玩命提示中',{time: 1000});
    //加载树
    !function(){
        $.ajax({
            url: "/core/menuTree/getMenuTree.do",
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data != null && data.length > 0) {
                    //渲染
                    var inst1 = tree.render({
                        id: 'myTree',
                        elem: '#my-tree',  //绑定元素
                        accordion: true,//开启手风琴模式
                        onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
                        data: data,
                        click: function(e) { //节点选中状态改变事件监听，全选框有自己的监听事件
                            console.log("aaa"); //得到checkbox原始DOM对象
                            console.log(data); //得到checkbox原始DOM对象

                            //节点选中效果
                            var active = "layui-bg-green layui-active";
                            $("#my-tree .layui-tree-entry").removeClass(active);
                            $(e.elem).find(".layui-tree-entry:first").addClass(active);

                            selData = tree.getSelected('myTree');
                        }
                    });
                }

            },
            error: function (jqXHR) {
                console.log(jqXHR);
            }
        });
    }();

    //按钮绑定事件
    $(".move-btn").on('click',function(){
        if(selData == null){
            layer.msg('请先选中一个菜单节点');
            return;
        }
        var type = $(this).data("type");
        console.log(type);
    });

});