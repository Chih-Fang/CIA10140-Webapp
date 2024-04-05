package movie;

import java.util.List;
import java.util.Map;



	

public interface movieDAO_Interface{
          public void insert(MovieVO movie);
          public void update(MovieVO movie);
          public void delete(Integer movieId);
          public MovieVO findByPrimaryKey(Integer movieId);
          public List<MovieVO> getAll();
          
          
     
}


