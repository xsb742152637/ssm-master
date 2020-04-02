layui.use(['tree','layer','form'], function(){
    let tree = layui.tree,
        form = layui.form,
        layer = layui.layer;
    let selData = null;//菜单树选中的信息
    //加载树
    let loadTree = function(){
        layer.load();
        $.ajax({
            url: "/core/memberInfo/getMainInfo.do",
            dataType: 'json',
            type: "POST",
            success: function (data) {
                layer.close();
                console.log(data);
                if (data != null && data.length > 0) {
                    //渲染
                    let inst1 = tree.render({
                        id: 'myTree',
                        elem: '#my-tree',  //绑定元素
                        // accordion: true,//开启手风琴模式
                        onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
                        selectedId: (selData == null ? '' : selData.memberId),//默认选中
                        data: data,
                        loadSuccess: function(e){
                            $(e.elem).find('.layui-tree-set').each(function(){
                                let data = $(this).data('data');
                                //停用的标签暗字显示
                                if(data != null && !data.isShow){
                                    $(this).find('.layui-tree-txt:first').addClass('layui-tree-color2');
                                }
                            });
                        },
                        click: function(e) { //节点选中状态改变事件监听，全选框有自己的监听事件
                            console.log(e);
                            if(e.selected){
                                selData = e.data;
                                tree_edit();
                            }else{
                                selData = null;
                                // Function.setForm($('.layui-form'),null,form);
                            }
                        }
                    });
                }
            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });
    };

    loadTree();

    let _check = function(){
        if(selData == null){
            layer.msg('请先选中一个菜单节点');
            return false;
        }
        return true;
    };

    //编辑
    let tree_edit = function(){
        if(!_check())
            return;
        let data = $.extend({}, selData);
        Function.setForm($('.layui-form'),data,form);
    };

    //删除当前菜单
    $('.btn-del').on('click',function(){
        if(!_check())
            return;
        layer.confirm('确认删除当前人员及其下级？', {btn: ['确定','取消']},
            function(){
                layer.load();
                $.ajax({
                    url: "/core/memberInfo/deleteMain.do",
                    dataType: 'json',
                    type: "POST",
                    data: {mainId: selData.memberId},
                    success: function (rs) {
                        layer.res(rs);
                        if(!rs.error){
                            layer.close();
                            loadTree();
                        }
                    },
                    error: function (jqXHR) {
                        layer.close();
                        console.log(jqXHR);
                    }
                });
            }, function(){});

    });

    //新增下一级菜单
    $('.btn-add').on('click',function(){
        if(!_check())
            return;
        let data = {isShow: true,parentId: selData.memberId};
        Function.setForm($('.layui-form'),data,form);
    });

    //移动
    $('.btn-move').on('click',function(){
        if(!_check())
            return;
        let type = $(this).data('type');
        layer.load();
        $.ajax({
            url: "/core/memberInfo/moveMain.do",
            dataType: 'json',
            type: "POST",
            data: {mainId: selData.memberId,type: type},
            success: function (rs) {
                layer.res(rs);
                if(!rs.error){
                    layer.close();
                    loadTree();
                }
            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });
    });

    //提交表单
    form.on('submit(formDemo)', function(data){
        if(!_check())
            return;
        console.log(data.field);
        layer.load();
        $.ajax({
            url: "/core/memberInfo/saveMain.do",
            dataType: 'json',
            type: "POST",
            data: data.field,
            success: function (rs) {
                console.log(rs);
                layer.res(rs);
                if(!rs.error){
                    layer.close();
                    loadTree();
                }
            },
            error: function (jqXHR) {
                layer.close();
                layer.res('保存失败！');
                console.log(jqXHR);
            }
        });
        return false;
    });

});