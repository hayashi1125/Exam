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

@WebServlet("/action/kamoku_list")
public class KamokuList extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    HttpSession sess = request.getSession();
    Teacher teacher = (Teacher) sess.getAttribute("teacher");
    if (teacher == null) {
      response.sendRedirect("../login.jsp");
      return;
    }
    String schoolCd = teacher.getSchool_cd();

    List<Map<String,Object>> subjects = new ArrayList<>();
    try (Connection con = new DAO().getConnection();
         PreparedStatement st = con.prepareStatement(
           "SELECT cd, name FROM SUBJECT WHERE school_cd = ? ORDER BY cd")) {
      st.setString(1, schoolCd);
      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          Map<String,Object> m = new HashMap<>();
          m.put("cd", rs.getString("cd"));
          m.put("name", rs.getString("name"));
          subjects.add(m);
        }
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }

    request.setAttribute("subjects", subjects);
    request.getRequestDispatcher("/kamoku/kamoku_list.jsp").forward(request, response);
  }
}