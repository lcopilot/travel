<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--网站图标-->
    <link rel="shortcut icon" href="img/icons8_face_id_16px.png"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
    <style rel="stylesheet/scss" lang="scss" scoped>
        .el-alert__title, .el-alert__icon {
            font-size: 20px;
        }

        .regInfo {
            width: 200px;
            height: 200px;
            overflow: hidden;
            border: 1px solid #ccc;
            border-radius: 50%;
            margin: 0 auto;
            position: relative;

        .tip {
            position: absolute;
            top: 0;
            width: 100%;
            height: 40px;
            line-height: 40px;
            background: rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        .canvas {
            position: absolute;
            top: 0;
            width: 100%;
        }

        }
        .titleInfo {
            text-align: center;
            font-weight: 700;
            padding: 30px 0 20px;
        }

        .tipShow {
            position: absolute;
            top: 50%;
            left: 50%;
            width: 180px;
            margin-left: -90px;
        }
    </style>
    <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%--<p align="center">
    <button id="open">开启摄像头</button>
    <button id="close">关闭摄像头</button>
    <button id="CatchCode">拍照</button>
</p>--%>
<div class="login-container">
    <div class="login-form" v-show="canvasShow">
        <h3><p class="titleInfo">请把正脸对准摄像头</p></h3>
        <div class="regInfo" id="faceCartoon">
            <%--识别动画--%>

            <%--视频流,自动播放--%>
            <div align="center" class="canvas">
                <video id="video" width="273px" height="206px" autoplay></video>
                <canvas hidden="hidden" id="canvas" width="626" height="800"></canvas>
            </div>
        </div>
        <p class="titleInfo" id="FaceCheck_Hint">正在识别...</p>
    </div>
</body>
<script type="text/javascript">
  var video;//视频流对象
  var context;//绘制对象
  var canvas;//画布对象
  var flag = false;
  //页面加载完成
  $(function () {
    //判断摄像头是否打开
    if (!flag) {
      //调用摄像头初始化
      open();
      flag = true;
    }
    //拍照将数据传到后台
    setTimeout('Photograph(flag);', 1300);
  });

  function Timeout() {
    alert("您的验证已超时,请重试");
    document.location = "login.jsp;"
  }

  <%--//开启摄像头
   $("#open").click(function() {
     //判断摄像头是否打开
       if (!flag) {
           //调用摄像头初始化
           open();
          flag = true;
       }
   });
   //关闭摄像头
   $("#close").click(function() {
      //判断摄像头是否打开
       if (flag) {
          video.srcObject.getTracks()[0].stop();
          flag = false;
       }
   });--%>

  //拍照
  function Photograph(flag) {
    if (flag) {
      context.drawImage(video, 0, 0, 330, 250);
      $("#FaceCheck_Hint").text("正在识别...");
      $("#faceCartoon").prepend(" <div class=\"lds-css ng-scope\" id=\"discernCartoon\">\n"
          + "\n"
          + "                <div style=\"width:100%;height:100%\" class=\"lds-double-ring\">\n"
          + "                    <div></div>\n"
          + "                    <div></div>\n"
          + "                    <div>\n"
          + "                        <div></div>\n"
          + "                    </div>\n"
          + "                    <div>\n"
          + "                        <div></div>\n"
          + "                    </div>\n"
          + "                </div>\n"
          + "                <style type=\"text/css\">@keyframes lds-double-ring {\n"
          + "                                           0% {\n"
          + "                                               -webkit-transform: rotate(0);\n"
          + "                                               transform: rotate(0);\n"
          + "                                           }\n"
          + "                                           100% {\n"
          + "                                               -webkit-transform: rotate(360deg);\n"
          + "                                               transform: rotate(360deg);\n"
          + "                                           }\n"
          + "                                       }\n"
          + "\n"
          + "                @-webkit-keyframes lds-double-ring {\n"
          + "                    0% {\n"
          + "                        -webkit-transform: rotate(0);\n"
          + "                        transform: rotate(0);\n"
          + "                    }\n"
          + "                    100% {\n"
          + "                        -webkit-transform: rotate(360deg);\n"
          + "                        transform: rotate(360deg);\n"
          + "                    }\n"
          + "                }\n"
          + "\n"
          + "                @keyframes lds-double-ring_reverse {\n"
          + "                    0% {\n"
          + "                        -webkit-transform: rotate(0);\n"
          + "                        transform: rotate(0);\n"
          + "                    }\n"
          + "                    100% {\n"
          + "                        -webkit-transform: rotate(-360deg);\n"
          + "                        transform: rotate(-360deg);\n"
          + "                    }\n"
          + "                }\n"
          + "\n"
          + "                @-webkit-keyframes lds-double-ring_reverse {\n"
          + "                    0% {\n"
          + "                        -webkit-transform: rotate(0);\n"
          + "                        transform: rotate(0);\n"
          + "                    }\n"
          + "                    100% {\n"
          + "                        -webkit-transform: rotate(-360deg);\n"
          + "                        transform: rotate(-360deg);\n"
          + "                    }\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring {\n"
          + "                    position: relative;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring div {\n"
          + "                    box-sizing: border-box;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div {\n"
          + "                    position: absolute;\n"
          + "                    width: 168px;\n"
          + "                    height: 168px;\n"
          + "                    top: 16px;\n"
          + "                    left: 16px;\n"
          + "                    border-radius: 50%;\n"
          + "                    border: 8px solid #000;\n"
          + "                    border-color: #51CACC transparent #51CACC transparent;\n"
          + "                    -webkit-animation: lds-double-ring 0.8s linear infinite;\n"
          + "                    animation: lds-double-ring 0.8s linear infinite;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(2),\n"
          + "                .lds-double-ring > div:nth-child(4) {\n"
          + "                    width: 148px;\n"
          + "                    height: 148px;\n"
          + "                    top: 26px;\n"
          + "                    left: 26px;\n"
          + "                    -webkit-animation: lds-double-ring_reverse 0.8s linear infinite;\n"
          + "                    animation: lds-double-ring_reverse 0.8s linear infinite;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(2) {\n"
          + "                    border-color: transparent #9DF871 transparent #9DF871;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(3) {\n"
          + "                    border-color: transparent;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(3) div {\n"
          + "                    position: absolute;\n"
          + "                    width: 100%;\n"
          + "                    height: 100%;\n"
          + "                    -webkit-transform: rotate(45deg);\n"
          + "                    transform: rotate(45deg);\n"
          + "                }\n"
          + "                .lds-double-ring > div:nth-child(3) div:before,\n"
          + "                .lds-double-ring > div:nth-child(3) div:after {\n"
          + "                    content: \"\";\n"
          + "                    display: block;\n"
          + "                    position: absolute;\n"
          + "                    width: 8px;\n"
          + "                    height: 8px;\n"
          + "                    top: -8px;\n"
          + "                    left: 72px;\n"
          + "                    background: #51CACC;\n"
          + "                    border-radius: 50%;\n"
          + "                    box-shadow: 0 160px 0 0 #51CACC;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(3) div:after {\n"
          + "                    left: -8px;\n"
          + "                    top: 72px;\n"
          + "                    box-shadow: 160px 0 0 0 #51CACC;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(4) {\n"
          + "                    border-color: transparent;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(4) div {\n"
          + "                    position: absolute;\n"
          + "                    width: 100%;\n"
          + "                    height: 100%;\n"
          + "                    -webkit-transform: rotate(45deg);\n"
          + "                    transform: rotate(45deg);\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(4) div:before,\n"
          + "                .lds-double-ring > div:nth-child(4) div:after {\n"
          + "                    content: \"\";\n"
          + "                    display: block;\n"
          + "                    position: absolute;\n"
          + "                    width: 8px;\n"
          + "                    height: 8px;\n"
          + "                    top: -8px;\n"
          + "                    left: 62px;\n"
          + "                    background: #9DF871;\n"
          + "                    border-radius: 50%;\n"
          + "                    box-shadow: 0 140px 0 0 #9DF871;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring > div:nth-child(4) div:after {\n"
          + "                    left: -8px;\n"
          + "                    top: 62px;\n"
          + "                    box-shadow: 140px 0 0 0 #9DF871;\n"
          + "                }\n"
          + "\n"
          + "                .lds-double-ring {\n"
          + "                    width: 200px !important;\n"
          + "                    height: 200px !important;\n"
          + "                    -webkit-transform: translate(-100px, -100px) scale(1) translate(100px, 100px);\n"
          + "                    transform: translate(-100px, -100px) scale(1) translate(100px, 100px);\n"
          + "                }\n"
          + "                </style>\n"
          + "            </div>");
      CatchCode();
    } else {
      alert("获取摄像头权限失败,请重试");
    }

  }

  //将当前图像传输到后台
  function CatchCode() {
    //获取图像
    var img = getBase64();
    //Ajax将Base64字符串传输到后台处理
    $.ajax({
      type: "POST",
      url: "user/faceLogin",
      data: {
        img: img
      },
      dataType: "JSON",
      success: function (data) {
        //返回的结果
        if (data.FaceCheck_msg == 3) {
          var myTime = setTimeout('Photograph(flag);', 250);
          //设置页面超时
          setTimeout('Timeout()', 25000);
          $("#FaceCheck_Hint").text("没有检测到人脸");
          $("#discernCartoon").remove();
        } else if (data.FaceCheck_msg == 1) {
          //关闭摄像头
          // video.srcObject.getTracks()[0].stop();
          //提醒用户识别成功
          //alert("验证成功!");
          //验证成功跳转页面
          window.location.href = "index.html;"
        } else if (data.FaceCheck_msg == 2) {
          alert("用户不存在!");
        }
      }
    });

  };

  //开启摄像头
  function open() {
    //获取摄像头对象
    canvas = document.getElementById("canvas");
    context = canvas.getContext("2d");
    //获取视频流
    video = document.getElementById("video");
    var videoObj = {
      "video": true
    }, errBack = function (error) {
      console.log("Video capture error: ", error.code);
    };
    context.drawImage(video, 0, 0, 330, 250);
    //初始化摄像头参数
    if (navigator.getUserMedia || navigator.webkitGetUserMedia
        || navigator.mozGetUserMedia) {
      navigator.getUserMedia = navigator.getUserMedia
          || navigator.webkitGetUserMedia
          || navigator.mozGetUserMedia;
      navigator.getUserMedia(videoObj, function (stream) {
        video.srcObject = stream;
        video.play();
      }, errBack);
    }
  }

  //将摄像头拍取的图片转换为Base64格式字符串
  function getBase64() {
    //获取当前图像并转换为Base64的字符串
    var imgSrc = canvas.toDataURL("image/png");
    //截取字符串
    return imgSrc.substring(22);
  };

</script>


</html>