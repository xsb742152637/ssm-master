layui.use(['tree','layer','form'], function(){
    let tree = layui.tree, layer = layui.layer, form = layui.form;
    let selData = null;//菜单树选中的信息

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
                            //节点选中效果
                            var active = "layui-bg-green layui-active";
                            $("#my-tree .layui-tree-entry").removeClass(active);
                            $(e.elem).find(".layui-tree-entry:first").addClass(active);

                            selData = tree.getSelected('myTree');
                            tree_edit();
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
    $('.btn-move').on('',function(){
        if(!_check)
            return;
        let type = $(this).data('type');
        console.log(type);
    });

    //新增下一级菜单
    $('.btn-add').on('click',function(){
        if(!_check)
            return;
        $('.tree-form input[name="type"]').val('add');
    });

    //删除当前菜单
    $('.btn-del').on('click',function(){
        if(!_check)
            return;

    });

    //修改菜单
    let tree_edit = function(){
        if(!_check)
            return;
        $('.tree-form input,.tree-form select').each(function(){
            let name = $(this).attr('name');
            $(this).val(selData[name]);
        });
        $('.tree-form input[name="type"]').val('edit');
        console.log(selData);
    };

    let _check = function(){
        if(selData == null){
            layer.msg('请先选中一个菜单节点');
            return false;
        }
        return true;
    }
});