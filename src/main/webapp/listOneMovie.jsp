<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="movie.*"%>
<%@ page import="java.util.List" %>

<%
MovieVO movie = (MovieVO) request.getAttribute("movieVO");


int id=0;
MovieJDBCDAO test=new MovieJDBCDAO();
List<MovieVO> movies=test.getAll();
for(MovieVO a:movies){
	id=a.getMovieId();
	
}

%>

<html>
<head>
    <title>電影資料新增成功</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #ccccff;
        }
        h3 {
            text-align: center;
            margin-top: 20px;
        }
        a {
            color: blue;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h3>電影資料 -新增成功囉! </h3>
    <table>
        <tr>
            <th>電影編號</th>
            <th>電影名稱</th>
            <th>電影時長</th>
            <th>上映日期</th>
            <th>電影類型</th>
            <th>電影語言</th>
            <th>電影分級</th>
            <th>電影狀態</th>
            <th>電影圖片</th>
            
        </tr>
            <tr>
            <td><%= id%></td>
            <td><%= movie.getMovieName() %></td>
            <td><%= movie.getRuntime() %></td>
            <td><%= movie.getReleaseDate() %></td>
            <td><%= movie.getGenre() %></td>
            <td><%= movie.getLanguage() %></td>
            <td><%= movie.getRating() %></td>
            <td><%= movie.getMovieStatus() %></td>
             <td>
    <img src="data:image/jpeg;base64,<%= movie.getPicBase64() %>" alt="Movie Image"  style="width: 100px; height: auto;">
        </td>

        </tr>
    </table>
    <div style="text-align: center; margin-top: 20px;">
        <a href="add.jsp">繼續新增</a>
    </div>
</body>
</html>
