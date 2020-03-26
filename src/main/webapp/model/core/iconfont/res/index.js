layui.use(['element'], function(){
    var element = layui.element;

    //搜索
    $("input[name='title']").on('input',function(){
        var v = $(this).val();
        if(String.isNullOrWhiteSpace(v)){
            $('.my-icons li').show();
        }else{
            $('.my-icons li').each(function(i,o){
                var name = $(o).find(".doc-icon-name").text();
                var fc = $(o).find(".doc-icon-fontclass").text();

                if(name.indexOf(v) >= 0 || fc.indexOf(v) >= 0){
                    $(o).show();
                }else{
                    $(o).hide();
                }
            });
        }
        console.log();
    });

    //刷新
    $(".my-refresh").on('click',function(){
        $("input[name='title']").val("").trigger("input");
    });
});