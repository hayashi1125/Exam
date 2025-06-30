package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.Teacher;

@WebServlet("/disp/studentinsertform")
public class StudentInsertForm extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Teacher teacher = (Teacher) session.getAttribute("teacher");

    if (teacher == null) {
      response.sendRedirect("../login.jsp");
      return;
    }

    try {
      // 年リスト作成（現在年 ±10年）
      int currentYear = Year.now().getValue();
      List<Integer> entYearList = new ArrayList<>();
      for (int i = currentYear - 10; i <= currentYear + 10; i++) {
        entYearList.add(i);
      }

      // DBから class_num を school_cd で取得
      List<String> classNumList = new ArrayList<>();
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/kaihatsu");
      Connection con = ds.getConnection();

      PreparedStatement st = con.prepareStatement(
        "SELECT class_num FROM class_num WHERE school_cd = ? ORDER BY class_num"
      );
      st.setString(1, teacher.getSchool_cd());
      ResultSet rs = st.executeQuery();

      while (rs.next()) {
        classNumList.add(rs.getString("class_num"));
      }

      rs.close();
      st.close();
      con.close();

      // JSP に渡す
      request.setAttribute("ent_year_list", entYearList);
      request.setAttribute("class_num_list", classNumList);
      request.getRequestDispatcher("/disp/studentinsert.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
