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
                    $("#shortURL").val(url_result.short_url);
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
    <input type="text" id="fullURL"><button onclick="clickdid();">변환!</button>
    <input type="text" id="shortURL">
</body>
</html>

