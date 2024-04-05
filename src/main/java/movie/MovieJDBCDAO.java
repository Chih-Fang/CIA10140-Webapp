package movie;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class MovieJDBCDAO implements movieDAO_Interface {
	String driveString = "com.mysql.cj.jdbc.Driver";

	String url = "jdbc:mysql://localhost:3306/cinema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String password = "168168";

	@Override
	public void insert(MovieVO movie) {

		String sql = "INSERT INTO movie (movieName, runtime, genre, releaseDate, language,rating,movieStatus,pic) VALUES (?, ?, ?, ?, ?, ?,?,?)";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, movie.getMovieName());
			ps.setInt(2, movie.getRuntime());
			ps.setString(3, movie.getGenre());
			ps.setObject(4, movie.getReleaseDate());
			ps.setString(5, movie.getLanguage());
			ps.setString(6, movie.getRating());
			ps.setString(7, movie.getMovieStatus());
			ps.setBytes(8, movie.getPic());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(MovieVO movie) {
		String sql = "UPDATE movie set movieName=?,runtime=?, genre=?,releaseDate=?,language=?, rating=?,moviesStatus=?,pic=? where movieId = ?";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, movie.getMovieName());
			ps.setInt(2, movie.getRuntime());
			ps.setString(3, movie.getGenre());
			ps.setObject(4, movie.getReleaseDate());
			ps.setString(5, movie.getLanguage());
			ps.setString(6, movie.getRating());
			ps.setString(7, movie.getMovieStatus());

			ps.setInt(8, movie.getMovieId());
			ps.setBytes(9, movie.getPic());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<MovieVO> getAll() {
		List<MovieVO> movies = new ArrayList<>();

		String sql = "select movieId, movieName, runtime, genre, releaseDate, language,rating,movieStatus,pic from movie;";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				int movieId = rs.getInt(1);
				String movieName = rs.getString(2);
				int runtime = rs.getInt(3);
				String genre = rs.getString(4);
				LocalDate releaseDate = rs.getObject(5, LocalDate.class);
				String language = rs.getString(6);
				String rating = rs.getString(7);
				String movieStatus = rs.getString(8);
				byte[] pic = rs.getBytes(9);
				MovieVO movie = new MovieVO(movieId, movieName, runtime, genre, releaseDate, language, rating,
						movieStatus, pic);
				movies.add(movie);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(movies);
		return movies;
	}



	@Override
	public void delete(Integer movieId) {

		String sql = "DELETE FROM movie where movieId = ?;";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, movieId);
         ps.executeUpdate();		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public MovieVO findByPrimaryKey(Integer movieId) {

		String sql = "Select movieId ,movieName,releaseDate,runtime FROM movie where movieId = ?;";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = null;

			MovieVO movie = new MovieVO();

			ps.setInt(1, movieId);
			rs = ps.executeQuery();
			while (rs.next()) {
				movie.setMovieId(rs.getInt("movieId"));
				movie.setMovieName(rs.getString("movieName"));
				
				LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
				movie.setReleaseDate(releaseDate);
				movie.setRuntime(rs.getInt("runtime"));
			}
			System.out.println(movie);
	

		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("====");
		}

	  return null;
	}
	
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	public static void main(String[] args) {
		
		//新增一筆資料
		MovieJDBCDAO dao = new MovieJDBCDAO();
//		
//		
		MovieVO movie1=new MovieVO();
		movie1.setMovieName("刺激1995");
		movie1.setRuntime(new Integer(144));
		movie1.setGenre("d");
        movie1.setReleaseDate(LocalDate.of(1994, 02, 19));
        movie1.setLanguage("英文");
        movie1.setRating("輔導級");
        
        byte[] pic;
		try {
			pic = getPictureByteArray("src/main/webapp/images/shawshank.jpg");
			 movie1.setPic(pic);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dao.insert(movie1);
		System.out.println("新增成功");
//     
		dao.findByPrimaryKey(1);
		System.out.println("=====================");
//		
		dao.getAll();
//		

	}

}
