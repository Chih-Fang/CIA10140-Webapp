package movie;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/MovieServlet")
@MultipartConfig(maxFileSize = 16177216)
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MovieServlet() {
		super();

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

  // ================================我是快速僧尋電影關鍵字====================================

		if ("searchkeywords".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			String movieName = req.getParameter("movieName");
			String movieNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)\u0020]{1,30}$";

			if (movieName == null || movieName.trim().length() == 0) {
				errorMsgs.add("電影名稱: 請勿空白");
			} else if (!movieName.trim().matches(movieNameReg)) {
				errorMsgs.add("電影名字: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
				System.out.println(movieName);
			}

			MovieVO movie1 = new MovieVO();

			movie1.setMovieName(movieName);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("movieVO", movie1); 
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher failureView = req.getRequestDispatcher("/selectPage.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			MovieService movieSvc = new MovieService();
			List<MovieVO> movies;

			movies = movieSvc.keywords(movieName);
			req.setAttribute("movies", movies);
			System.out.println("hahahahaha");

			String url = "/listonandoff.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);

		}

//	====================我式查詢即將上映電影或是熱映電影==========================	

		if ("searchMovie".equals(action)) {
			String status = req.getParameter("movieStatus");

			MovieService movieSvc = new MovieService();
			List<MovieVO> movies;
			if ("on".equals(status)) {

				movies = movieSvc.findmovieStatus("熱映中");
				req.setAttribute("movies", movies);

			} else {

				movies = movieSvc.findmovieStatus("即將上映");
				System.out.println(movies);

				req.setAttribute("movies", movies);
         
			}

			String url = "/listonandoff.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}

		

		//===========================我是新增一筆資料=================================/
		if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

		

			String movieName = req.getParameter("movieName");
			String movieNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
			if (movieName == null || movieName.trim().length() == 0) {
				errorMsgs.add("電影名稱: 請勿空白");
			} else if (!movieName.trim().matches(movieNameReg)) {
				errorMsgs.add("電影名字: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
				System.out.println(movieName);
			}

			Integer runtime = null;
			try {
				runtime = Integer.valueOf(req.getParameter("runtime").trim());

			} catch (NumberFormatException e) {
				errorMsgs.add("請輸入電影時長!");
			}

			LocalDate releaseDate = null;

			String releaseDateParam = req.getParameter("releaseDate");
			if (releaseDateParam != null && !releaseDateParam.isEmpty()) {
				try {
					releaseDate = LocalDate.parse(releaseDateParam);
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入有效的上映日期，格式應為yyyy-MM-dd");
				}
			} else {
				errorMsgs.add("請輸入上映日期!");
			}

			String language = req.getParameter("language");
			if (!"英文".equals(language) && !"中文".equals(language)) {
				errorMsgs.add("請選語言!");

			} else {

				if (language == null || language.trim().length() == 0) {
					errorMsgs.add("請選語言!");
				}

			}
			String genre = req.getParameter("genre");

			if (genre == null || genre.trim().length() == 0) {
				errorMsgs.add("請輸入電影分類!");
			}

			String rating = req.getParameter("rating");

			if (rating == null || rating.trim().length() == 0) {
				errorMsgs.add("請輸入電影級別!");
			}

			String movieStatus = req.getParameter("movieStatus");

			if (movieStatus == null || movieStatus.trim().length() == 0) {
				errorMsgs.add("請輸入電影上映狀態!");
			}
			

			Part pic = req.getPart("pic");
			
			if(pic.getSize() == 0) {
				errorMsgs.add("請新增電影海報!");
			}

			InputStream is = pic.getInputStream();

			MovieVO movie1 = new MovieVO();

			movie1.setMovieName(movieName);
			movie1.setRuntime(runtime);
			movie1.setGenre(genre);
			movie1.setReleaseDate(releaseDate);
			movie1.setLanguage(language);
			movie1.setRating(rating);
			movie1.setMovieStatus(movieStatus);


			byte[] imageData = null;
			BufferedInputStream bis = new BufferedInputStream(is);  //高階資料流
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); //等等要把輸入流東西東西都寫進
			try {
				int bytesRead;
				byte[] buffer = new byte[8192];
				while ((bytesRead = bis.read(buffer)) != -1) {
					baos.write(buffer, 0, bytesRead);
				}
				imageData = baos.toByteArray();
				
				
			} catch (IOException e) {

			} finally {
				try {
					bis.close();
					baos.close();
					is.close();
				} catch (IOException e) {
					
				}
			}
			movie1.setPic(imageData);

//		

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("movieVO", movie1); 
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher failureView = req.getRequestDispatcher("/add.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			//=====================2.開始新增資料================================
			MovieService movieSvc = new MovieService();
			movie1 = movieSvc.addMovie(movieName, runtime, genre, releaseDate, language, rating, movieStatus,
					imageData);
			System.out.println("hahahahaha");
			//新增成功後導入畫面
			req.setAttribute("movieVO", movie1); 
			String url = "/listOneMovie.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
		}

		if ("editMovie".equals(action)) { // 來自listAllMOvie編輯的請求

			

			/*************************** 1.接收請求參數 ****************************************/
			Integer movieId = Integer.valueOf(req.getParameter("movieId"));
			System.out.println("c8 c8 c8 " + movieId);

			//=====================查詢電影編號================================
			MovieService movieSvc = new MovieService();
			MovieVO movie1 = movieSvc.getOneMovie(movieId);

			//==============編輯請求針對電影編號查詢然後存到REQ================
			req.setAttribute("movieVO", movie1); 
			String url = "/updateMovie.jsp";

			RequestDispatcher successView = req.getRequestDispatcher(url);  //導到編輯頁面
			successView.forward(req, res);
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer movieId = Integer.valueOf(req.getParameter("movieId"));
			String movieName = req.getParameter("movieName");
			String movieNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)\u0020]{2,30}$";
			if (movieName == null || movieName.trim().length() == 0) {
				errorMsgs.add("電影名稱: 請勿空白");
			} else if (!movieName.trim().matches(movieNameReg)) {
				errorMsgs.add("電影名字: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間");
				
			}

			Integer runtime = null;
			try {
				runtime = Integer.valueOf(req.getParameter("runtime").trim());

			} catch (NumberFormatException e) {
				errorMsgs.add("電影時長輸入格式錯誤");
			}

			LocalDate releaseDate = null;

			String releaseDateParam = req.getParameter("releaseDate");
			if (releaseDateParam != null && !releaseDateParam.isEmpty()) {
				try {
					releaseDate = LocalDate.parse(releaseDateParam);
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入有效的上映日期，格式應為yyyy-MM-dd");
				}
			} else {
				errorMsgs.add("請輸入上映日期!");
			}

			String language = req.getParameter("language");
//			if (!"英文".equals(language) && !"中文".equals(language)) {
//				errorMsgs.add("請選語言");
//
//			} else {
//
//				if (language == null || language.trim().length() == 0) {
//					errorMsgs.add("請選語言");
//				}
//
//			}
			String genre = req.getParameter("genre");

			if (genre == null || genre.trim().length() == 0) {
				errorMsgs.add("請輸入電影分類");
			}

			String rating = req.getParameter("rating");

			if (rating == null || rating.trim().length() == 0) {
				errorMsgs.add("請輸入電影級別");
			}

			String movieStatus = req.getParameter("movieStatus");

			if (movieStatus == null || movieStatus.trim().length() == 0) {
				errorMsgs.add("請輸入電影上映狀態");
			}
//		

			MovieVO movie1 = new MovieVO();
			movie1.setMovieId(movieId);
			movie1.setMovieName(movieName);
			movie1.setRuntime(runtime);
			movie1.setGenre(genre);
			movie1.setReleaseDate(releaseDate);
			movie1.setLanguage(language);
			movie1.setRating(rating);
			movie1.setMovieStatus(movieStatus);

		
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("movieVO", movie1); // 原本錯誤的電影資訊也一起存在REQUEST
				req.setAttribute("errorMsgs", errorMsgs);
				String url = "/updateMovie.jsp";

				RequestDispatcher fail = req.getRequestDispatcher(url);
			fail.forward(req, res);
				return; // 程式中斷
			}

			//=====================2.開始新增資料================================
			MovieService movieSvc = new MovieService();
			movie1 = movieSvc.updateMovie(movieId, movieName, runtime, genre, releaseDate, language, rating,
					movieStatus);
        System.out.println("更新成功!!!!!!!!!");
			
			
			//=========================更新後導回原本電影清單=====================
			req.setAttribute("movieVO", movie1); 
			String url = "/listAllMovie.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,回到原本畫面
			successView.forward(req, res);
		}
		

		if ("deleteMovie".equals(action)) { //listAllmovie 那隻JSP如果刪除時會跳轉來這

			List<String> errorMsgs = new LinkedList<String>();



			/*************************** 1.接收請求參數 ***************************************/
			Integer movieId = Integer.valueOf(req.getParameter("movieId"));

			/*************************** 2.開始刪除資料 ***************************************/
			MovieService movieSvc = new MovieService();
			movieSvc.deleteMovie(movieId);

			/*************************** 3.刪除完成,準備轉交(回去查詢全部電影) ***********/
			String url = "/listAllMovie.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}

}
