
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MyServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Set response content type
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Insert Data to DB table";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h2 align=\"center\">" + title + "</h2>\n" + //
	            "<ul>\n" + //

	            "  <li>Welcome to my unit tracking system! \n" + //
	            "  <li>Below are links to the Search and Insert pages for the database. \n" + //
	            "  <li>The search page also contains a button to clear the table's contents so you can make a fresh list.\n" + //

	            "</ul>\n");

	      out.println("<a href=/techexercise/simpleFormInsert.html>Insert Data</a> <br>");
	      out.println("<a href=/techexercise/simpleFormSearch.html>Search Data</a> <br>");
	      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
