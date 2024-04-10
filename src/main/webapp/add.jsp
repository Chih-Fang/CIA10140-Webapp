<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="movie.*"%>
<%@ page import="java.util.List" %>

<% 
   MovieVO movieVO = (MovieVO) request.getAttribute("movieVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>世界影城表單</title>

<head>
    <meta charset="UTF-8">
    <title>世界影城表單</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }
        form {
            margin: 20px auto;
            padding: 20px;
            width: 80%;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        input[type="text"],
        input[type="date"],
        select {
            width: calc(100% - 15px);
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        
        select {
            width: 980px;
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        li {
            color: red;
            margin-bottom: 5px;
        }
    </style>
</head>
</head>

<body>



<%
    // 获取errorMsgs对象并进行类型转换
    Object errorMsgsObj = request.getAttribute("errorMsgs");
    List<String> errorMsgs = null;
    if (errorMsgsObj instanceof List<?>) {
        errorMsgs = (List<String>) errorMsgsObj;
    }

    // 检查errorMsgs是否为空
    if(errorMsgs != null && !errorMsgs.isEmpty()) {
%>
        <!-- 如果errorMsgs不为空，则执行以下内容 -->
        <font style="color:red">請修正以下錯誤:</font>
        <ul>
            <% 
                // 使用forEach循环遍历errorMsgs列表，并输出错误消息
                for(String message : errorMsgs) {
            %>
                    <li style="color:red"><%= message %></li>
            <% 
                }
            %>
        </ul>
<%
    }
%>



	<form action="MovieServlet" method="post" name="form1" enctype="multipart/form-data">

		輸入電影名字<input type="text" name="movieName">  <br>                                                                                
		輸入電影時長(分)<input type="text" name="runtime"> <br> 
		電影類型 <input type="text" name="genre"><br> 
		上映時間<input type="date" name="releaseDate"><br> 
		語言<select name="language"> 
			
			<option value="英文" default>英文</option>
			<option value="中文">中文</option>

		</select>
		
		電影級別 <input type="text" name="rating"><br>
		 上映狀態 <input type="text" name="movieStatus"><br>
		 選擇海報：<input type="file" name="pic"><br><br>
		  <input type="hidden" name="action" value="insert"> 
			
			<input 	type="submit" value="addmovie">
		

	</form>







</body>
</html>