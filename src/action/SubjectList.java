package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.DAO;

@WebServlet("/action/subjectlist")
public class SubjectList extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession();
    Teacher teacher = (Teacher) session.getAttribute("teacher");
    if (teacher == null) {
      response.sendRedirect("../login_logout/login-in.jsp");
      return;
    }
    String schoolCd = teacher.getSchool_cd();



    List<Map<String, Object>> subjects = new ArrayList<>();

    try {
      DAO dao = new DAO();
      try (Connection con = dao.getConnection()) {
        try (PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE school_cd = ? ORDER BY cd")) {
          st.setString(1, schoolCd);
          try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
              Map<String, Object> subject = new HashMap<>();
              subject.put("cd", rs.getString("cd"));
              subject.put("name", rs.getString("name"));
              subjects.add(subject);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }

    request.setAttribute("subjects", subjects);

    // ★ここを修正：JSPは subject_list.jsp → kamoku_list.jsp に変更
    request.getRequestDispatcher("/kamoku/kamoku_list.jsp").forward(request, response);
  }
}