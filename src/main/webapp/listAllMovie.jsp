<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="movie.MovieVO"%>
<%@ page import="movie.MovieService"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>所有電影</title>
<style>
body {
	margin: 0;
	padding: 0;
	background-color: #f5f5f5;
}

h1 {
	text-align: center;
	margin-top: 20px;
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
	text-align: left;
}

th {
	background-color: #db9dd9c9;
}

.btn {
	padding: 5px 10px;
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
	
}

.btnd {
	padding: 5px 10px;
	background-color: red;
	color: white;
	border: none;
	
}

.btn:hover {
	background-color: #889db3;
	
}

.container {
	display: flex;
	justify-content: center;
	
}

.btn1{ padding:10px;}

.last {
	width: 50px;
}
</style>
</head>
<body>
	<h1>所有電影</h1>
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
			<th class="last">操作</th>
			<th></th>
		</tr>

		<%
		MovieService movieService = new MovieService();
		List<MovieVO> movieList = movieService.getAll();

		for (MovieVO movie : movieList) {
		%>
		<tr>
			<td><%=movie.getMovieId()%></td>
			<td><%=movie.getMovieName()%></td>
			<td><%=movie.getRuntime()%></td>
			<td><%=movie.getReleaseDate()%></td>
			<td><%=movie.getGenre()%></td>
			<td><%=movie.getLanguage()%></td>
			<td><%=movie.getRating()%></td>
			<td><%=movie.getMovieStatus()%></td>
			<td><img
				src="data:image/jpeg;base64,<%=movie.getPicBase64()%>"
				alt="Movie Image" style="width: 100px; height: auto;"></td>

			<td>
				<form action="MovieServlet" method="post" style="display: inline;">
					<input type="hidden" name="action" value="deleteMovie"> <input
						type="hidden" name="movieId" value="<%=movie.getMovieId()%>">
					<button type="submit" class="btnd">刪除</button>
				</form>
			</td>
			<td>
				<form action="MovieServlet" method="post" style="display: inline;">
					<input type="hidden" name="action" value="editMovie"> <input
						type="hidden" name="movieId" value="<%=movie.getMovieId()%>">
					<button type="submit" class="btn">編輯</button>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	
	<div class="container">
	
	<div class="btn1" style="text-align: center;">
		<a href="add.jsp" class="btn">新增電影</a>
	</div>

	<div class="btn1" style="text-align: center;">
		<a href="selectPage.jsp" class="btn">回首頁</a>
	</div>
	
	</div>
</body>
</html>
