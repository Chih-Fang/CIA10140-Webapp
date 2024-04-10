<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="movie.*"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>世界影城</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        margin: 0;
        padding: 0;
    }

    h4 {
        color: #333;
        font-size: 24px;
        margin-bottom: 20px;
        text-align: center;
    }

    a {
        text-decoration: none;
        color: #007bff;
        font-weight: bold;
        display: inline-block;
        padding: 10px;
        margin: 10px auto;
        width: 200px;
        text-align: center;
        background-color: #f0f0f0;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    a:hover {
        background-color: #007bff;
        color: #fff;
    }

    form {
        margin: 0 auto;
        text-align: center;
    }

    select, input[type="text"] {
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        margin-bottom: 10px;
    }

    button {
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }

    .link-group {
        text-align: center;
    }
</style>
</head>
<body>

<H4>HELLO!世界影城</H4>


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


<div class="link-group">
    <a href="http://localhost:8081/CIA101_C_myMaven1/add.jsp">新增電影</a>
    <a href="http://localhost:8081/CIA101_C_myMaven1/listAllMovie.jsp">操作全部電影</a>
</div>

<form action="MovieServlet" method="post" name="form1">
    <select name="movieStatus">
        <option value="on" default>熱映中</option>
        <option value="upcoming">即將上映</option>
    </select>
    <input type="hidden" name="action" value="searchMovie">
    <button type="submit">送出查詢</button>
</form>

<form action="MovieServlet" method="post" name="form2">
    輸入電影關鍵字<input type="text" id="movieName" name="movieName" >
    <input type="hidden" name="action" value="searchkeywords">
    <button type="submit">送出查詢</button>
</form>





</body>
</html>

