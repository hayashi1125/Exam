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

@WebServlet("/action/studentlist")
public class StudentList extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String entYear = request.getParameter("ent_year");
    String classNo = request.getParameter("class_no");
    String isAttend = request.getParameter("is_attend");

    // ログインユーザーのschool_cd取得
    HttpSession session = request.getSession();
    Teacher teacher = (Teacher) session.getAttribute("teacher");
    if (teacher == null) {
      response.sendRedirect("../login.jsp");
      return;
    }
    String schoolCd = teacher.getSchool_cd();

    List<Map<String, Object>> students = new ArrayList<>();
    List<Integer> entYears = new ArrayList<>();
    List<String> classNums = new ArrayList<>();

    try {
      DAO dao = new DAO();
      try (Connection con = dao.getConnection()) {

        // 入学年度一覧（ログイン教師の学校のみ）
        try (PreparedStatement st = con.prepareStatement(
            "SELECT DISTINCT ent_year FROM STUDENT WHERE school_cd = ? ORDER BY ent_year")) {
          st.setString(1, schoolCd);
          try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
              entYears.add(rs.getInt("ent_year"));
            }
          }
        }

        // クラス番号一覧（ログイン教師の学校のみ）
        try (PreparedStatement st = con.prepareStatement(
            "SELECT DISTINCT class_num FROM STUDENT WHERE school_cd = ? ORDER BY class_num")) {
          st.setString(1, schoolCd);
          try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
              classNums.add(rs.getString("class_num"));
            }
          }
        }

        // 学生一覧の絞り込み取得（ログイン教師の学校のみ）
        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENT WHERE school_cd = ?");
        if (entYear != null && !entYear.isEmpty()) {
          sql.append(" AND ent_year = ?");
        }
        if (classNo != null && !classNo.isEmpty()) {
          sql.append(" AND class_num = ?");
        }
        if ("1".equals(isAttend)) {
          sql.append(" AND is_attend = TRUE");
        }
        sql.append(" ORDER BY no");

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
          int index = 1;
          st.setString(index++, schoolCd);

          if (entYear != null && !entYear.isEmpty()) {
            st.setInt(index++, Integer.parseInt(entYear));
          }
          if (classNo != null && !classNo.isEmpty()) {
            st.setString(index++, classNo);
          }

          try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
              Map<String, Object> student = new HashMap<>();
              student.put("no", rs.getString("no"));
              student.put("name", rs.getString("name"));
              student.put("ent_year", rs.getInt("ent_year"));
              student.put("class_num", rs.getString("class_num"));
              student.put("is_attend", rs.getBoolean("is_attend"));
              students.add(student);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }

    request.setAttribute("students", students);
    request.setAttribute("entYears", entYears);
    request.setAttribute("classNums", classNums);

    request.getRequestDispatcher("/disp/student_list.jsp").forward(request, response);
  }
}
