<%@page import="java.time.LocalDate"%>
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
    <meta charset="UTF-8">
    <title>更新電影訊息</title>
    
    
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
        input[type="text"], select {
            width: calc(100% - 20px);
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"], input[type="button"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover, input[type="button"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>


<%
    List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");

    
    if(errorMsgs != null && !errorMsgs.isEmpty()) {
%>
        <!-- 如果errorMsgs不是空的舊執行下面 -->
        <font style="color:red">請修正以下錯誤:</font>
        <ul>
            <% 
                //列出每個錯誤
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







<form action="MovieServlet" method="post" name="form1">

<label for="movieId">電影編號：</label><br>
    <input type="text" disabled id="movieId" name="movieId" value="<%= movieVO.getMovieId()%>"><br>
    
    <label for="movieName">電影：</label><br>
    <input type="text" id="movieName" name="movieName" value="<%= movieVO.getMovieName()%>"><br>
    
    <label for="runtime">電影時長(分)：</label><br>
    <input type="text" id="runtime" name="runtime" value="<%= movieVO.getRuntime()%>"><br>
    
    <label for="genre">電影類型：</label><br>
    <input type="text" id="genre" name="genre" value="<%=movieVO.getGenre() %>"><br>
    
    <label for="releaseDate">上映時間：</label><br>
    <input type="date" id="releaseDate" name="releaseDate" value="<%= movieVO.getReleaseDate() %>"><br>
    
    <label for="language">語言：</label><br>
    <select id="language" name="language">
        <option value="英文" <%= movieVO.getLanguage().equals("英文") ? "selected" : "" %>>英文</option>
        <option value="中文" <%= movieVO.getLanguage().equals("中文") ? "selected" : "" %>>中文</option>
    </select><br>
    
    <label for="rating">電影級別：</label><br>
    <input type="text" id="rating" name="rating" value="<%=movieVO.getRating() %>"><br>
    
    <label for="movieStatus">上映狀態：</label><br>
    <input type="text" id="movieStatus" name="movieStatus" value="<%= movieVO.getMovieStatus() %>"><br>
    
    <input type="hidden" name="action" value="update"> 
    
    <input type="hidden"  id="movieId" name="movieId" value="<%= movieVO.getMovieId()%>"><br>
    
    <input type="submit" value="update">
    <input type="button" value="cancle">
</form>

</body>
</html>