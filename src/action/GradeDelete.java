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

@WebServlet("/action/gradedelete")
public class GradeDelete extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    // 成績削除に必要なパラメータを取得
    String subject = request.getParameter("subject");
    String examNoStr = request.getParameter("examNo");
    String message;

    try {
      int examNo = Integer.parseInt(examNoStr); // 試験回はint型前提

      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/kaihatsu");
      Connection con = ds.getConnection();

      // 成績削除SQL
      PreparedStatement st = con.prepareStatement(
        "DELETE FROM student WHERE subject = ? AND exam_no = ?"
      );
      st.setString(1, subject);
      st.setInt(2, examNo);
      int rows = st.executeUpdate();

      st.close();
      con.close();

      message = (rows > 0) ? "削除が完了しました。" : "削除対象の成績が見つかりませんでした。";

    } catch (Exception e) {
      e.printStackTrace();
      message = "エラーが発生しました。<br>" + e.getMessage();
    }

    request.setAttribute("message", message);
    request.getRequestDispatcher("/disp/grade_deleteresult.jsp").forward(request, response);
  }
}
