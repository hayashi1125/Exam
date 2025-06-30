package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.Teacher;

@WebServlet("/disp/studentupdateform")
public class StudentUpdateForm extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Teacher teacher = (Teacher) session.getAttribute("teacher");

    if (teacher == null) {
      response.sendRedirect("../login.jsp");
      return;
    }

    String studentNo = request.getParameter("no"); // パラメータ名に注意

    try {
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/kaihatsu");
      Connection con = ds.getConnection();

      // 学生情報を取得
      PreparedStatement st1 = con.prepareStatement(
        "SELECT no, name, ent_year, class_num, is_attend FROM student WHERE no = ?"
      );
      st1.setString(1, studentNo);
      ResultSet rs1 = st1.executeQuery();

      Map<String, Object> student = new HashMap<>();

      if (rs1.next()) {
        student.put("no", rs1.getString("no"));
        student.put("name", rs1.getString("name"));
        student.put("ent_year", rs1.getInt("ent_year"));
        student.put("class_num", rs1.getString("class_num"));
        student.put("is_attend", rs1.getBoolean("is_attend"));
      }

      rs1.close();
      st1.close();

      // クラス一覧を取得（ログインユーザーの school_cd に一致するもの）
      PreparedStatement st2 = con.prepareStatement(
        "SELECT class_num FROM class_num WHERE school_cd = ? ORDER BY class_num"
      );
      st2.setString(1, teacher.getSchool_cd());
      ResultSet rs2 = st2.executeQuery();

      List<String> classNumList = new ArrayList<>();
      while (rs2.next()) {
        classNumList.add(rs2.getString("class_num"));
      }

      rs2.close();
      st2.close();
      con.close();

      // JSPに渡す
      request.setAttribute("student", student);
      request.setAttribute("class_num_list", classNumList);
      request.getRequestDispatcher("/disp/studentupdate.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}

