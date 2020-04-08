layui.use(['table','layer','form','element'],function () {
    let table = layui.table, form = layui.form,layer = layui.layer,element = layui.element;

    let dicinfoTableId = "dicinfoTable";
    let dicinfoTable = table.render({
        elem:'#' + dicinfoTableId
        ,id:dicinfoTableId
        ,text:{
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        ,url:"/core/dicinfo/getMainInfo.do"
        ,defaultToolbarLeft: ['add', 'update','delete']
        ,defaultToolbarRight:''
        ,toolbar: 'default'
        ,loading: true //显示加载条,
        ,page: true //开启分页
        ,limit: 10 //每页条数
        ,limits: [5,10,50,100] //每页条数
        ,cols:[[
            {field: 'dicName', title: '字典名称', width:'50%', sort: true, fixed: 'left'}
            ,{field: 'dicCode', title: '字典编码', sort: true}
        ]]
        ,done: function(res, curr, count){
            //加载成功
        }

    });

    let dicdetailTableId = "dicdetailTable";
    let dicdetailTable = table.render({
        elem:'#' + dicdetailTableId
        ,id:dicdetailTableId
        ,text:{
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        ,url:"/core/dicdetail/getDetailInfo.do"
        ,where:{"dicId":'-1'}
        ,toolbar: 'default'
        ,defaultToolbarRight:''
        ,loading: true //显示加载条,
        ,page: true //开启分页
        ,limit: 10 //每页条数
        ,limits: [5,10,50,100] //每页条数
        ,cols:[[
            {field: 'detailName', title: '标题', width:'20%', fixed: 'left'}
            ,{field: 'detailCode', title: '编码',width:'20%'}
            ,{field: 'detailValue', title: '值',width:'20%'}
            ,{field: 'detailLevel', title: '顺序',sort: true, width:'10%'}
            ,{field: 'isValid', title: '是否有效',width:'10%'}
            ,{field: 'comment', title: '备注',width:'20%'}
        ]]
        ,done: function(res, curr, count){
            //加载成功
        }

    });




    //监听行单击事件（双击事件为：rowDouble）
    table.on('row(test)', function(obj){
        var data = obj.data;
        dicdetailTable.reload({
            where:{"dicId":data.dicId}
        });


        // layer.alert(JSON.stringify(data), {
        //     title: '当前行数据：'
        // });

        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });





    //检查
    // let _check = function(){
    //     let selectedRow = table.getSelectedRow(mainTableId);//选中行
    //     if(selectedRow == null){
    //         layer.msg('请选择一行数据');
    //         return false;
    //     }
    //     return selectedRow;
    // }
});