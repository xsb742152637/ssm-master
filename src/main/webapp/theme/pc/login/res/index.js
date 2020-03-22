$(function(){
    set_val();
    xz();
});

function set_val(){
    var dlInfo = window.localStorage.getItem('dlInfo');
    if(dlInfo != null){
        dlInfo = JSON.parse(dlInfo);
        $("input[name='username']").val(dlInfo.username);
        $("input[name='password']").val(dlInfo.password);
        $("input[name='savePas']").prop("checked",dlInfo.savePas);
    }
}
function login(){
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    var savePas = $("input[name='savePas']").is(":checked");

    var flg = false;
    if(username == ""){
        $.messager.alert('消息','请输入用户名','info');
        flg = true;
    }else if(password == ""){
        $.messager.alert('消息','请输入密码','info');
        flg = true;
    }
    if(flg) {
        return;
    }
    $.ajax({
        url: "/anon/loginservlet",
        dataType: 'json',
        type: "POST",
        data: {username: username,password: password},
        error: function (jqXHR) {
            console.log(jqXHR);
        },
        success: function (data) {
            console.log(data);
            if (data.error) {
                $.messager.alert('消息',data.msg,'info');
            } else {
                if(!savePas){
                    password = "";
                }
               // window.localStorage.removeItem('dlInfo');
                window.localStorage.setItem('dlInfo', JSON.stringify({username: username,password: password,savePas: savePas}));
                window.location.href = successUrl;
            }
        }
    });
}

function xz(){
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