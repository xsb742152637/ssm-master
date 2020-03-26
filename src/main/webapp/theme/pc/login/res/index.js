layui.use('form', function(){
    var $ = layui.jquery;
    var form = layui.form;

    xz();

    var dlInfo = window.localStorage.getItem('dlInfo');
    if(dlInfo != null){
        dlInfo = JSON.parse(dlInfo);
        $("input[name='username']").val(dlInfo.username);
        $("input[name='password']").val(dlInfo.password);
        $("input[name='savePas']").prop("checked",dlInfo.savePas);
    }
    layui.form.render();//重新渲染form表单
    //监听提交
    form.on('submit(formDemo)', function(data){
        console.log(data);
        $.ajax({
            url: "/anon/loginservlet",
            dataType: 'json',
            type: "POST",
            data: data.field,
            error: function (jqXHR) {
                console.log(jqXHR);
            },
            success: function (rs) {
                console.log(rs);
                if (rs.error) {
                    layer.msg(rs.msg);
                } else {
                    if(String.isNullOrWhiteSpace(data.field.savePas) || !Boolean.parse(data.field.savePas)){
                        data.field.password = "";
                    }
                    // window.localStorage.removeItem('dlInfo');
                    window.localStorage.setItem('dlInfo', JSON.stringify(data.field));
                    window.location.href = successUrl;
                }
            }
        });
        return false;
    });
});

var xz = function(){
    var s = 0;
    var m = 100;
    var fx = true;
    var t2 = window.setInterval(function() {
        if(s == m)
            fx = false;
        else if(s == -m)
            fx = true;

        if(fx){
            s++;
        }else{
            s--;
        }
        var r = 22.5 + s/20;
        $(".zfx-n1").css({transform: "rotate("+ r +"deg)",boxShadow: "0px "+ (20 + s/10) +"px 20px 5px #999 , 0px 10px 20px 0px #999 inset"});
        $(".zfx-n2").css({transform: "rotate("+ -r +"deg)",boxShadow: "0px "+ (20 + s/10) +"px 20px 5px #999 , 0px 10px 20px 0px #999 inset"});
    },25);
}