package movie;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.dao.support.DaoSupport;



public class MovieService {

	private movieDAO_Interface dao;

	public MovieService() {
		dao = new MovieJDBCDAO();
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	public MovieVO addMovie(String movieName, Integer runtime, String genre, LocalDate releaseDate, String language,
			String rating, String movieStatus, byte[] pic) {

		MovieVO movie1 = new MovieVO();

		movie1.setMovieName(movieName);
		movie1.setRuntime(runtime);
		movie1.setGenre(genre);
		movie1.setReleaseDate(releaseDate);
		movie1.setLanguage(language);
		movie1.setRating(rating);
		movie1.setMovieStatus(movieStatus);
        movie1.setPic(pic);
//			

			
		

	
		dao.insert(movie1);
	

		return movie1;
	}

	public MovieVO updateMovie( Integer movieId ,String movieName, Integer runtime, String genre, LocalDate releaseDate,
			String language, String rating, String movieStatus) {
		MovieVO movie1 = new MovieVO();
		movie1.setMovieId(movieId);
		movie1.setMovieName(movieName);
		movie1.setRuntime(runtime);
		movie1.setGenre(genre);
		movie1.setReleaseDate(releaseDate);
		movie1.setLanguage(language);
		movie1.setRating(rating);
        movie1.setMovieStatus(movieStatus);
//		movie1.setPic(pic);
        
		dao.update(movie1);
		return movie1;
	
	}
	
	
	public void deleteMovie(Integer movieId) {
		dao.delete(movieId);
	}

	public MovieVO getOneMovie(Integer movieId) {
		System.out.println("oooooooooo");
		return dao.findByPrimaryKey(movieId);
	}

	public List<MovieVO> getAll() {
		return dao.getAll();
	}
	
	
	public List<MovieVO> findmovieStatus(String movieStatus){
		
		return dao.onAndOff(movieStatus);
	}
	
	 public List<MovieVO> keywords(String movieName){
		 return dao.keywords(movieName);
	 }
	 
	 
	 
	 
	 
	public static void main(String[] args) {
		MovieService a=new MovieService();
		
		a.keywords("E");
		
		System.out.println(a.keywords("Escape"));
	}
	
	
	

}


