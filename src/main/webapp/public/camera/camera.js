
var Camera = function(option){
    var self = this;
    self.option = $.extend(true,{},self.defaultOption,option);
    self.init();
};

Camera.prototype = {
    defaultOption: {
        id: "",
        width: 900,
        height: 600,
        showSaveLocBut: true,
        showSaveServerBut: true
    },
    init: function(){
        var self = this;
        if (navigator.mediaDevices === undefined) {
            navigator.mediaDevices = {};
        }
        // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
        // 使用getUserMedia，因为它会覆盖现有的属性。
        // 这里，如果缺少getUserMedia属性，就添加它。
        if (navigator.mediaDevices.getUserMedia === undefined) {
            navigator.mediaDevices.getUserMedia = function (constraints) {
                // 首先获取现存的getUserMedia(如果存在)
                var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
                // 有些浏览器不支持，会返回错误信息
                // 保持接口一致
                if (!getUserMedia) {
                    return Promise.reject(new Error('getUserMedia is not implemented in this browser'));
                }
                //否则，使用Promise将调用包装到旧的navigator.getUserMedia
                return new Promise(function (resolve, reject) {
                    getUserMedia.call(navigator, constraints, resolve, reject);
                });
            }
        }

        var base_div = $("<div class='camera_div'></div>");
        base_div.append('<link rel="stylesheet" href="/public/camera/camera.css"/>');
        base_div.append('<script src="/public/html2canvas/html2canvas.js" type="text/javascript"></script>');

        var tool_div = $("<div class='camera_tool'></div>");
        tool_div.appendTo(base_div);
        //显示保存到本地的按钮
        if(self.option.showSaveLocBut){
            var div = $("<div></div>");
            div.attr("class","save_loc");
            div.html("保存到本地");
            div.appendTo(tool_div);
            div.on("click",function(){
                self.createCanvas();

                var fileName = new Date().getTime();
                Html2Canvas.saveFile($("#" + self.option.id + " #canvas")[0],fileName);
                $("#" + self.option.id + " #canvas").hide();
            });
        }
        //显示保存到服务器的按钮
        if(self.option.showSaveServerBut){
            var div = $("<div></div>");
            div.attr("class","save_server");
            div.html("保存到服务器");
            div.appendTo(tool_div);
            div.on("click",function(){
                var img = self.createCanvas();
                $("#" + self.option.id + " #canvas").hide();

                $.ajax({type: "post",url: "/model/saveImage.do", dataType: "json", async: false,data: {image:img},
                    error: function(e){
                        console.log(e);
                        alert("保存失败！")
                    },
                    success: function (data, textStatus, jqXHR) {
                        alert(data.msg)
                    }
                });
            });
        }

        base_div.append('<video id="my_video" width="'+ self.option.width +'" height="'+ self.option.height +'" autoplay></video><canvas style="display:none;" id="canvas" width="'+self.option.width+'" height="'+self.option.height+'"></canvas>');
        base_div.appendTo($("#" + self.option.id));


        var constraints = { audio: false, video: {width: 1920,height:768} };
        navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
            var video = document.querySelector("video");
            // 旧的浏览器可能没有srcObject
            if ("srcObject" in video) {
                video.srcObject = stream;
            } else {
                //避免在新的浏览器中使用它，因为它正在被弃用。
                video.src = window.URL.createObjectURL(stream);
            }
            video.onloadedmetadata = function (e) {
                video.play();
            };
        }).catch(function (err) {
            console.log(err.name + ": " + err.message);
        });

    },
    createCanvas: function(){
        var self = this;

        $("#" + self.option.id + " #canvas").show();
        var canvas = $("#" + self.option.id + " #canvas")[0];
        console.log(canvas)
        var context = canvas.getContext("2d");
        // 点击，canvas画图
        context.drawImage($("#" + self.option.id + " #my_video")[0], 0, 0, self.option.width, self.option.height);
        // 获取图片base64链接
        var image = canvas.toDataURL('image/png');
        // 定义一个img
        var img = new Image();
        //设置属性和src
        img.id = "imgBoxxx";
        img.src = image;
        //将图片添加到页面中
        document.body.appendChild(img);

        // base64转文件
        // function dataURLtoFile(dataurl, filename) {
        //     var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        //         bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
        //     while (n--) {
        //         u8arr[n] = bstr.charCodeAt(n);
        //     }
        //     return new File([u8arr], filename, {type: mime});
        // }
        //
        // console.log(dataURLtoFile(image, 'aa.png'));
        return image;
    }
};