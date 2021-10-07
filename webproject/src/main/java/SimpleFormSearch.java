import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword1 = request.getParameter("keyword1");
      String keyword2 = request.getParameter("keyword2");
      search(keyword1, keyword2, response);
   }

   void search(String keyword1, String keyword2, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;

         //No data branch
         if (keyword1.isEmpty() && keyword2.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableTech";
            preparedStatement = connection.prepareStatement(selectSQL);
         } 
         //If only first field filled in
         else if (keyword2.isEmpty())
         {
            String selectSQL = "SELECT * FROM MyTableTech WHERE UNIT_NAME LIKE ?";
            String theUnitName = keyword1 + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUnitName);
         }
         //If only second field filled in
         else if (keyword1.isEmpty())
         {
        	 String selectSQL = "SELECT * FROM MyTableTech WHERE UNIT_SIZE LIKE ?";
             String theUnitSize = keyword2 + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theUnitSize);
         }
         //Both branch
         else
         {
        	 String selectSQL = "SELECT * FROM MyTableTech WHERE UNIT_NAME LIKE ? && UNIT_SIZE LIKE ?";
             String theUnitName = keyword1 + "%";
             String theUnitSize = keyword2 + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theUnitName);
             preparedStatement.setString(2, theUnitSize);
         }
         ResultSet rs = preparedStatement.executeQuery();

         int sum = 0;
         while (rs.next()) {
            int id = rs.getInt("id");
            String unitName = rs.getString("unit_name").trim();
            String unitSize = rs.getString("unit_size").trim();
            String unitPoint = rs.getString("unit_point").trim();
            String unitAddl = rs.getString("unit_addl").trim();

            if (keyword1.isEmpty() || unitName.contains(keyword1)) {
               out.println("ID: " + id + ", ");
               out.println("Unit Name: " + unitName + ", ");
               out.println("Unit Size: " + unitSize + ", ");
               out.println("Unit Points: " + unitPoint + ", ");
               out.println("Unit Additional Info: " + unitAddl + "<br>");
               sum += Integer.parseInt(unitPoint);
            }
         }
         out.println("Total Points: " + sum + "<br>");
         out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
