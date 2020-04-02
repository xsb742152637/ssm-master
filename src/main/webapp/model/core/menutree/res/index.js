layui.use(['tree','layer','form'], function(){
    let tree = layui.tree, form = layui.form,layer = layui.layer;

    let selData = null;//菜单树选中的信息
    //加载树
    let loadTree = function(){
        layer.load();
        $.ajax({
            url: "/core/menuTree/getMenuTree.do",
            dataType: 'json',
            type: "POST",
            success: function (data) {
                layer.close();
                if (data != null && data.length > 0) {
                    //渲染
                    let inst1 = tree.render({
                        id: 'myTree',
                        elem: '#my-tree',  //绑定元素
                        accordion: true,//开启手风琴模式
                        onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
                        selectedId: (selData == null ? '' : selData.menuId),//默认选中
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
                            // console.log(e);
                            if(e.selected){
                                selData = e.data;
                                tree_edit();
                            }else{
                                selData = null;
                                Function.setForm($('.layui-form'),null,form);
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

    //提交表单
    form.on('submit(formDemo)', function(data){
        if(!_check())
            return;
        console.log(data.field);
        layer.load();
        $.ajax({
            url: "/core/menuTree/saveMain.do",
            dataType: 'json',
            type: "POST",
            data: data.field,
            success: function (rs) {
                layer.res(rs);
                console.log(rs);
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

    //移动
    $('.btn-move').on('click',function(){
        if(!_check())
            return;
        let type = $(this).data('type');
        layer.load();
        $.ajax({
            url: "/core/menuTree/moveMain.do",
            dataType: 'json',
            type: "POST",
            data: {mainId: selData.menuId,type: type},
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

    //删除当前菜单
    $('.btn-del').on('click',function(){
        if(!_check())
            return;
        layer.confirm('确认删除当前菜单及其下级？', {btn: ['确定','取消']},
            function(){
                layer.load();
                $.ajax({
                    url: "/core/menuTree/deleteMain.do",
                    dataType: 'json',
                    type: "POST",
                    data: {mainId: selData.menuId},
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
        let data = {isShow: true,parentId: selData.menuId};
        Function.setForm($('.layui-form'),data,form);
    });

    //修改菜单
    let tree_edit = function(){
        if(!_check())
            return;
        let data = $.extend({}, selData);
        Function.setForm($('.layui-form'),data,form);
    };

    //加载应用列表
    !function(){
        layer.load();
        $.ajax({
            url: "/core/menuurl/getMenuUrl.do",
            dataType: 'json',
            type: "POST",
            success: function (data) {
                layer.close();
                if (data != null && data.length > 0) {
                    //渲染
                    var str = '<option value="" >请选择一个应用</option>';
                    for(let i = 0 ; i < data.length ; i++){
                        let d = data[i];
                        str += '<option value="'+ d.urlId +'">'+ d.title +'</option>';
                    }
                    $('.layui-form select[name="urlId"]').empty().append(str);
                    form.render();
                }

            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });
    }();

    //应用选择监听
    form.on('select(urlSel)',function(data){
        let v = data.value;
        $(data.elem).find('option').each(function(){
            let vv = $(this).attr('value');
            if(String.isNullOrWhiteSpace(vv)){
                return;
            }
            if(vv.equals(v)){
                $('.layui-form input[name="title"]').val($(this).text());
            }
        });
        console.log(data);
    });

    //图标选择器
    $('.layui-form .layui-icon-more-vertical').on('click',function(){
        layer.iconSelector($('.layui-form input[name="icon"]'));
    });
    let _check = function(){
        console.log(selData);
        if(selData == null){

            layer.msg('请先选中一个菜单节点');
            return false;
        }
        return true;
    }
});