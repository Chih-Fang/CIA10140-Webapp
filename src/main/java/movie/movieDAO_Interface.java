package movie;

import java.util.List;
import java.util.Map;



	

public interface movieDAO_Interface{
          public void insert(MovieVO movie);
          public void update(MovieVO movie);
          public void delete(MovieVO movie);
          public MovieVO findByPrimaryKey(Integer movie_id);
          public List<MovieVO> getAll();
          
          
     
}


