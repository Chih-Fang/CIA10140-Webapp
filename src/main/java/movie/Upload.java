
package movie;
import java.io.*;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@MultipartConfig
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Upload() {
		super();
		
	}

	
	

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("image/jpeg");

		PrintWriter out = res.getWriter();
		Part photo = request.getPart("photo");
		String filename = photo.getSubmittedFileName();

		
		 int bytesRead;
		try (InputStream in = photo.getInputStream();
				OutputStream out1 = new FileOutputStream("C:\\CIA101_WebApp\\eclipse_WTP_workspace1\\CIA10140-Webapp\\"+ filename);) {
			out1.write(in.readAllBytes());
			
			
	
             byte[] buffer = new byte[4096]; 
             while ((bytesRead = in.read(buffer)) != -1) {
                 res.getOutputStream().write(buffer, 0, bytesRead);  
             }
             in.close();
		} catch (Exception e) {
			e.getStackTrace();
		}



	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	
	
	
	
	

}



