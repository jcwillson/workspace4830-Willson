
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String unitName = request.getParameter("uName");
      String unitSize = request.getParameter("uSize");
      String unitPoint = request.getParameter("uPoint");
      String unitAddl = request.getParameter("uAddl");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableTech (id, UNIT_NAME, UNIT_SIZE, UNIT_POINT, UNIT_ADDL) values (default, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, unitName);
         preparedStmt.setString(2, unitSize);
         preparedStmt.setString(3, unitPoint);
         preparedStmt.setString(4, unitAddl);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

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

            "  <li><b>Unit Name</b>: " + unitName + "\n" + //
            "  <li><b>Unit Size</b>: " + unitSize + "\n" + //
            "  <li><b>Unit Points</b>: " + unitPoint + "\n" + //
            "  <li><b>Unit Additional Info</b>: " + unitAddl + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject/simpleFormInsert.html>Insert Data</a> <br>");
      out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
