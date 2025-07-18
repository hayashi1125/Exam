package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/action/subjectdelete")
public class SubjectDelete extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    String cd = request.getParameter("cd");
    String message;

    try {
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/kaihatsu");
      Connection con = ds.getConnection();

      PreparedStatement st = con.prepareStatement("DELETE FROM SUBJECT WHERE cd = ?");
      st.setString(1, cd);
      int rows = st.executeUpdate();

      st.close();
      con.close();

      message = (rows > 0) ? "削除が完了しました。" : "削除に失敗しました。";

    } catch (Exception e) {
      e.printStackTrace();
      message = "エラーが発生しました。<br>" + e.getMessage();
    }

    request.setAttribute("message", message);
    request.getRequestDispatcher("/kamoku/subject_deleteresult.jsp").forward(request, response);
  }
}