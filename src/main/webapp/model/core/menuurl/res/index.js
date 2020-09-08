
layui.use(['table','layer','form'], function(){
    let table = layui.table, form = layui.form,layer = layui.layer;

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
        id: 'mainTable'
        ,url: '/core/menuurl/getMainInfo.do' //数据接口
        ,defaultToolbarLeft: canUpdate ? ['reset','search', 'add', 'update','delete'] : ['reset','search']
        ,cols: [[ //表头
            {field: 'order', title: '序号',type: 'numbers', width:80},
            {field: 'code', title: '应用编码', width: '20%',cellMinWidth: 100, sort: true},
            {field: 'title', title: '应用名称', width: '20%',cellMinWidth: 100},
            {field: 'url', title: '应用路径',cellMinWidth: 200},
            {field: 'parameter', title: '参数', width: '10%',cellMinWidth: 100},
            {field: 'sysTime', title: '编制时间', width: '15%',cellMinWidth: 100, sort: true,templet: function(d){
                return d.sysTime == null ? '' : Date.parseObj(d.sysTime).format('yyyy-MM-dd HH:mm');
            }}
        ]]
        ,done: function(res, curr, count){
            //加载成功
        }
    });

    //按钮事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                openModel(null,'新增');
                break;
            case 'update':
                let d = _check();
                if(d == false){
                    return;
                }
                openModel(d,'修改：' + d.title);
                break;
            case 'delete':
                let dd = _check();
                if(dd == false){
                    return;
                }
                layer.confirm('确认删除数据：'+ dd.title +'？', {btn: ['确定','取消']},
                    function(){
                        layer.load();
                        $.ajax({
                            url: "/core/menuurl/deleteMain.do",
                            dataType: 'json',
                            type: "POST",
                            data: {primaryId: dd.urlId},
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

    //打开模态框
    let openModel = function(data,title){
        Function.setForm($('.my-model'),data,form);//表单填充
        layer.open({
            title: title,
            type: 1,
            area: '35%',
            content: $('.my-model')
        });
    };

    //提交表单
    form.on('submit(formDemo)', function(data){
        layer.load();
        $.ajax({
            url: "/core/menuurl/saveMain.do",
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

    //检查
    let _check = function(){
        let d = mainTable.getSelectedRow();//选中行
        console.log(d);
        if(d == null){
            layer.msg('请选择一行数据');
            return false;
        }
        return d;
    }
});