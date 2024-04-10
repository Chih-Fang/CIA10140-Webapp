package movie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class MovieVO implements java.io.Serializable{ 

private Integer movieId;
private String movieName;
private LocalDate releaseDate;
private Integer runtime;
private String genre;
private String rating;
private String movieInfo;
private String language;
private String movieStatus;
private byte[] pic;




public MovieVO(Integer movieId,String movieName,Integer runtime,String genre,LocalDate releaseDate,String language,String rating,String movieStatus,byte[] pic) {
	this.movieId=movieId;
	this.movieName=movieName;
	this.releaseDate=releaseDate;
	this.runtime=runtime;
	this.genre=genre;
	this.rating=rating;
	this.language=language;
	this.movieStatus=movieStatus;
	this.pic=pic;
			
}







public MovieVO() {
	super();
}




public Integer getMovieId() {
	return movieId;
}
public void setMovieId(Integer movieId) {
	this.movieId = movieId;
}
public String getMovieName() {
	return movieName;
}
public void setMovieName(String movieName) {
	this.movieName = movieName;
}
public LocalDate getReleaseDate() {
	return releaseDate;
}
public void setReleaseDate(LocalDate releaseDate) {
	this.releaseDate = releaseDate;
}
public Integer getRuntime() {
	return runtime;
}
public void setRuntime(Integer runtime) {
	this.runtime = runtime;
}
public String getGenre() {
	return genre;
}
public void setGenre(String genre) {
	this.genre = genre;
}
public String getRating() {
	return rating;
}
public void setRating(String rating) {
	this.rating = rating;
}
public String getMovieInfo() {
	return movieInfo;
}
public void setMovieInfo(String movieInfo) {
	this.movieInfo = movieInfo;
}
public String getLanguage() {
	return language;
}
public void setLanguage(String language) {
	this.language = language;
}
public String getMovieStatus() {
	return movieStatus;
}
public void setMovieStatus(String movieStatus) {
	this.movieStatus = movieStatus;
}

public byte[] getPic() {
	return pic;
}



public void setPic(byte[] pic) {
	this.pic = pic;
}







public String toString() {
	String text = String.format(
			"movieId: %s, movie name: %s, release date: %s,runtime:%s,genre:%s",
			movieId, movieName,releaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),runtime,genre);
		return text;
}
public String getPicBase64() {
    if (pic != null) {
        return Base64.getEncoder().encodeToString(pic);
    } 
    else {
        return ""; // 或者其他默认值，视情况而定
    }
}


}
