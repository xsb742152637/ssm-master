var layer;
layui.use(['table','layer','form'], function(){
    let table = layui.table, form = layui.form;
    layer = layui.layer;

    //第一个实例
    var mainTableId = 'mainTable';
    let mainTable = table.render({
        elem: '#mainTable'
        ,id: mainTableId
        ,text: '暂无数据'
        // ,height: 500
        ,url: '/core/menuurl/getMainInfo.do' //数据接口
        ,toolbar: 'default'
        ,loading: true //显示加载条
        ,page: true //开启分页
        ,limit: 10 //每页条数
        ,limits: [5,10,50,100] //每页条数
        ,cols: [[ //表头
            {field: 'order', title: '序号',type: 'numbers', width:80},
            {field: 'title', title: '应用名称', width: '20%',cellMinWidth: 100},
            {field: 'code', title: '应用编码', width: '20%',cellMinWidth: 100, sort: true},
            {field: 'url', title: '应用路径',cellMinWidth: 200},
            {field: 'parameter', title: '参数', width: '20%',cellMinWidth: 100},
            {field: 'sysTime', title: '编制时间', width: '10%',cellMinWidth: 100, sort: true}
        ]]
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            // console.log(res);

            //得到当前页码
            // console.log(curr);

            //得到数据总量
            // console.log(count);
        }
    });

    //按钮事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                layer.msg('添加');
                break;
            case 'update':
                let d = _check();
                if(d == false){
                    return;
                }

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
                            data: {mainId: dd.urlId},
                            success: function (rs) {
                                layer.close();
                                layer.res(rs);
                                table.reload();
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

    //行单击事件
    table.on('row(test)', function(obj){
        let index = $(obj.tr).data('index');
        let data = obj.data;
        table.setSelectedRow(mainTableId,index);//选中行
    });

    let _check = function(){
        let d = table.getSelectedRow(mainTableId);//选中行
        if(d == null){
            layer.msg('请选择一行数据');
            return false;
        }
        return d;
    }
});