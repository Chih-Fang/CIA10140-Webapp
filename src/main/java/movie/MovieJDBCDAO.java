package movie;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
		String sql = "UPDATE movie set movieName=?,runtime=?, genre=?,releaseDate=?,language=?, rating=?,movieStatus=? where movieId = ?";
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
//			ps.setBytes(9, movie.getPic());

			ps.executeUpdate();
			
			System.out.println("woooo");

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
				byte[] pic=rs.getBytes(9);
//				byte[] pic = null; //預設一個空的 ;
//		        InputStream in = rs.getBinaryStream(9);
//		        if (in != null) {
//		            pic = new byte[in.available()];
//		            in.read(pic);
//		            in.close();
//		        }
				
				MovieVO movie = new MovieVO(movieId, movieName, runtime, genre, releaseDate, language, rating,
						movieStatus,pic);
				
				
				//第二種寫法
//				MovieVO movie=new MovieVO();
//				movie.setMovieId(rs.getInt("movieId"));
//				movie.setMovieName(rs.getString("movieName"));
//				LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
//				movie.setReleaseDate(releaseDate);
//				movie.setRuntime(rs.getInt("runtime"));
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
		MovieVO movie=null;
		String sql = "Select movieId ,movieName,runtime,releaseDate,genre,language,rating, movieStatus,pic FROM movie where movieId = ?;";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = null;

			 movie = new MovieVO();

			ps.setInt(1, movieId);
			rs = ps.executeQuery();
			while (rs.next()) {
				movie.setMovieId(rs.getInt("movieId"));
				movie.setMovieName(rs.getString("movieName"));
				
				LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
				movie.setReleaseDate(releaseDate);
				movie.setRuntime(rs.getInt("runtime"));
				movie.setGenre(rs.getString("genre"));
				movie.setLanguage(rs.getNString("language"));
				movie.setMovieStatus(rs.getString("movieStatus"));
				movie.setRating(rs.getString("Rating"));
//				movie.setPic(rs.getBytes("pic"));
                byte[] pic = null; 
		        
		        InputStream in = rs.getBinaryStream(9);
		        if (in != null) {
		            pic = new byte[in.available()];
		            in.read(pic);
		           System.out.println("hahahahahaahahah"); 
		            in.close();
		        }
		        movie.setPic(pic);
		        
			}
			System.out.println(movie);
	

		} catch (Exception e) {
			e.printStackTrace();
//			
		}

	  return movie;
	}
	
	@Override
	 public List<MovieVO> onAndOff(String movieStatus) {
		List<MovieVO> movies = new ArrayList<>();
		String sql = "Select movieId ,movieName,runtime,releaseDate,genre,language,rating, movieStatus,pic FROM movie where movieStatus = ?;";
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {
		
			ps.setString(1, movieStatus);
			ResultSet rs = null;
			rs = ps.executeQuery();

		
		
			while (rs.next()) {
				
				MovieVO movie=new MovieVO();
				movie.setMovieId(rs.getInt("movieId"));
				movie.setMovieName(rs.getString("movieName"));
				
				LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
				movie.setReleaseDate(releaseDate);
				movie.setRuntime(rs.getInt("runtime"));
				movie.setGenre(rs.getString("genre"));
				movie.setLanguage(rs.getNString("language"));
				movie.setMovieStatus(rs.getString("movieStatus"));
				movie.setRating(rs.getString("rating"));
				
                   byte[] pic = null; 
		        
		        InputStream in = rs.getBinaryStream("pic");
		        if (in != null) {
		            pic = new byte[in.available()];
		            in.read(pic);
		           System.out.println("hahahahahaahahah"); 
		            in.close();
		        }
		        movie.setPic(pic);
				movies.add(movie);
				
			
				System.out.println(movie);
			}
			
	

		} catch (Exception e) {
			e.printStackTrace();
//			
		}

	  return movies;
		
		
	}
	

	@Override
	 public List<MovieVO> keywords(String movieName){
		
		List<MovieVO> movies = new ArrayList<>();
		String sql = "SELECT movieId, movieName, runtime, releaseDate, genre, language, rating, movieStatus,pic FROM movie WHERE movieName LIKE ?;";
				
		try (Connection connection = DriverManager.getConnection(url, userid, password);
				PreparedStatement ps = connection.prepareStatement(sql)) {
		
			ps.setString(1, "%" + movieName + "%");
			ResultSet rs = null;
			rs = ps.executeQuery();

		
		
			while (rs.next()) {
				
				MovieVO movie=new MovieVO();
				movie.setMovieId(rs.getInt("movieId"));
				movie.setMovieName(rs.getString("movieName"));
				
				LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
				movie.setReleaseDate(releaseDate);
				movie.setRuntime(rs.getInt("runtime"));
				movie.setGenre(rs.getString("genre"));
				movie.setLanguage(rs.getNString("language"));
				movie.setMovieStatus(rs.getString("movieStatus"));
				movie.setRating(rs.getString("rating"));
				
                   byte[] pic = null; 
		        
		        InputStream in = rs.getBinaryStream("pic");
		        if (in != null) {
		            pic = new byte[in.available()];
		            in.read(pic);
		           System.out.println("hahahahahaahahah"); 
		            in.close();
		        }
		        movie.setPic(pic);
				movies.add(movie);
			
			}
			
	

		} catch (Exception e) {
			e.printStackTrace();
//			
		}

	  return movies;
		
		 
		 
	 }
	
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	
	
	
	public static void main(String[] args) {
//		
//		//新增一筆資料
		MovieJDBCDAO dao = new MovieJDBCDAO();
////		
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
			
			e.printStackTrace();
		}
		
		dao.insert(movie1);
		System.out.println("新增成功");
////     
//		dao.findByPrimaryKey(1);
//		System.out.println("=====================");
////		
//		dao.getAll();
//		
//		dao.delete(1);
		
		dao.onAndOff("即將上映");
////		

	}

}
