<%--
  Created by IntelliJ IDEA.
  User: wany
  Date: 2020-02-01
  Time: 오후 3:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    $(function(){
        $('#copy_btn').click(function(){

            // a의 텍스트값을 가져옴
            var text = $("#shortURL").html();
            //숨겨진 input박스 value값으로 text 변수 넣어줌.
            $('#clip_target').val(text);
            // input박스 value를 선택
            $('#clip_target').select();
            var successful = document.execCommand('copy');
            if (successful) {
                alert('url 주소가 복사되었습니다.');
            } else {
                alert('url 주소가 복사되지 않았습니다.');
            }
        })
    });

    function clickdid(){
        if($("#fullURL").val().length < 5){
            alert("정확한 URL을 입력해주세요.");
            return;
        }else if($("#fullURL").val().length > 2000){
            alert("2000자 이하로 입력해주세요.");
            return;
        }

        //http, https 입력하게
        if($("#fullURL").val().indexOf("http://") != -1 || $("#fullURL").val().indexOf("https://") != -1){
            $.ajax({
                url:"url/getShortcut",
                type:"GET",
                data : {"original_url" : $("#fullURL").val()},
                success : function(result){
                    var url_result = JSON.parse(result.shortURL);
                    var url = window.location.protocol + "//" + window.location.host + "/" + url_result.short_url;
                    $("#resultDiv").show();
                    $("#shortURL_s").text("변환된주소 : ");
                    $("#shortURL").attr("href", url);
                    $("#shortURL").attr("data-clipboard-text", url);
                    $("#shortURL").text(url);
                },
                error : function(){
                    alert("알맞은 http:// 혹은 https:// 넣어주세요");
                }
            })
        }else{
            alert("알맞은 http:// 혹은 https://를 넣어주세요.");
            return;
        }
    }

</script>
<html>
<head>
    <title>brief</title>
</head>
<body>
    <div style="position:absolute;top:10%;width:100%;text-align:center;">
        <img src="resources/img/logo.png"/>
    </div>
    <div style="position:absolute;top:45%;width:100%;text-align:center;">
        <input type="text" id="fullURL" style="width: 55%; height: 70px; font-size: 16pt;  padding: 10px;border-radius: 10px; border: 2px solid #d4c7c7; vertical-align: top;">
        <img src="resources/img/magic_stick.png" style="width:70px;cursor:pointer;" onclick="clickdid();"/>
    </div>

    <div id="resultDiv" style="position:absolute;top:65%;width:100%;text-align:center;font-weight:bold;font-size:14pt;display: none;">
        <span id="shortURL_s" style="vertical-align: top;"></span>
        <a id="shortURL" style="vertical-align:top;color:cornflowerblue"></a>
        <input id="clip_target" style='position:absolute;top:-2000px;'/>
        <img id="copy_btn" style="width:30px;cursor: pointer;" src="resources/img/share.png"/>
    </div>
</body>
</html>

