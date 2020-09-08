layui.use(['table','layer','form','element'],function () {
    let table = layui.table, form = layui.form,layer = layui.layer,element = layui.element;

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
            }
        }
    });

    let mainTable = table.render({
        id: "mainTable"
        ,url:"/core/dicinfo/getMainInfo.do"
        ,defaultToolbarLeft: canUpdate ? ['add', 'update','delete'] : ''
        ,defaultToolbarRight:''//不要右边的按钮
        ,cols:[[
            {field: 'dicCode', title: '字典编码', sort: true}
            ,{field: 'dicName', title: '字典名称', width:'40%', sort: true}
            ,{field: 'dicDes', title: '字典描述', width:'30%'}
        ]]
        ,done: function(res, curr, count){
            //加载成功
        }
    });

    let detailTable = table.render({
        id:"detailTable"
        ,url:"/core/dicdetail/getDetailInfo.do"
        ,where:{"dicId":'-1'}
        ,defaultToolbarLeft: canUpdate ? ['reset','search', 'add', 'update','delete'] : ['reset','search']
        ,defaultToolbarRight:''
        ,cols:[[
            {field: 'detailCode', title: '编码',width:'15%',sort: true}
            ,{field: 'detailName', title: '标题', width:'20%',sort: true}
            ,{field: 'detailValue', title: '值',width:'15%',sort: true}
            ,{field: 'detailLevel', title: '顺序', width:'15%',sort: true}
            ,{field: 'isValid', title: '状态',width:'15%',sort: true,templet: function(d){
                    return Boolean.parse(d.isValid) ? '启用' : '停用';
                }}
            ,{field: 'comment', title: '备注',width:'20%'}
        ]]
        ,done: function(res, curr, count){
            //加载成功
        }

    });

    //监听行单击事件（双击事件为：rowDouble）
    table.on('row(main)', function(obj){
        let data = mainTable.getSelectedRow();//考虑到可以取消行的选中状态，所以用此方法
        console.log(obj);
        detailTable.reload({where:{"dicId": data == null ? '-1' : data.dicId}});
    });

    //按钮事件
    table.on('toolbar(main)', function(obj){
        switch(obj.event){
            case 'add':
                openModel('main-model',null,'新增');
                break;
            case 'update':
                let d = _check(true);
                if(d == false){
                    return;
                }
                openModel('main-model',d,'修改：' + d.dicName);
                break;
            case 'delete':
                let dd = _check(true);
                if(dd == false){
                    return;
                }
                layer.confirm('确认删除数据：'+ dd.dicName +'？', {btn: ['确定','取消']},
                    function(){
                        layer.load();
                        $.ajax({
                            url: "/core/dicinfo/deleteMain.do",
                            dataType: 'json',
                            type: "POST",
                            data: {primaryId: dd.dicId},
                            success: function (rs) {
                                layer.close();
                                layer.res(rs);
                                if(!rs.error){
                                    mainTable.reload();
                                }
                            },
                            error: function (jqXHR) {
                                layer.close();
                                console.log(jqXHR);
                            }
                        });
                    }, function(){});
                break;
        };
    });

    //按钮事件
    table.on('toolbar(det)', function(obj){
        switch(obj.event){
            case 'add':
                let d1 = _check(true);//这里必须选中主表
                if(d1 == false){
                    return;
                }
                openModel('detail-model',{dicId: d1.dicId},'新增');
                break;
            case 'update':
                let d = _check(false);
                if(d == false){
                    return;
                }
                openModel('detail-model',d,'修改：' + d.detailName);
                break;
            case 'delete':
                let dd = _check(false);
                if(dd == false){
                    return;
                }
                layer.confirm('确认删除数据：'+ dd.detailName +'？', {btn: ['确定','取消']},
                    function(){
                        layer.load();
                        $.ajax({
                            url: "/core/dicdetail/deleteDetail.do",
                            dataType: 'json',
                            type: "POST",
                            data: {detailId: dd.detailId},
                            success: function (rs) {
                                layer.close();
                                layer.res(rs);
                                if(!rs.error){
                                    detailTable.reload();
                                }
                            },
                            error: function (jqXHR) {
                                layer.close();
                                console.log(jqXHR);
                            }
                        });
                    }, function(){});
                break;
        };
    });

    //打开模态框
    let openModel = function(ele,data,title){
        Function.setForm($('.' + ele),data,form);//表单填充
        layer.open({
            title: title,
            type: 1,
            area: '35%',
            content: $('.' + ele)
        });
    };

    //提交表单
    form.on('submit(saveMain)', function(data){
        layer.load();
        $.ajax({
            url: "/core/dicinfo/saveMain.do",
            dataType: 'json',
            type: "POST",
            data: data.field,
            success: function (rs) {
                layer.res(rs);
                if(!rs.error){
                    layer.closeAll();
                    mainTable.reload();
                }else{
                    layer.close();
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

    //提交表单
    form.on('submit(saveDetail)', function(data){
        layer.load();
        $.ajax({
            url: "/core/dicdetail/saveDetail.do",
            dataType: 'json',
            type: "POST",
            data: data.field,
            success: function (rs) {
                layer.res(rs);
                if(!rs.error){
                    layer.closeAll();
                    detailTable.reload();
                }else{
                    layer.close();
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


    //检查
    let _check = function(type){
        let d = null;
        if(type){
            d = mainTable.getSelectedRow();//选中行
        }else{
            d = detailTable.getSelectedRow();//选中行
        }

        if(d == null){
            layer.msg('请选择一行数据');
            return false;
        }
        return d;
    }
});