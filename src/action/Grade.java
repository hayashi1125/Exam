package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.DAO;

@WebServlet("/action/grade")
public class Grade extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response); // GET は POST に委譲
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ▼ ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            response.sendRedirect("../login.jsp");
            return;
        }

        String schoolCd = teacher.getSchool_cd();

        // ▼ 検索パラメータの取得
        String selectedYear = request.getParameter("f1");
        String selectedClass = request.getParameter("f2");
        String selectedSubject = request.getParameter("f3");
        String studentNo = request.getParameter("f4");

        List<Map<String, Object>> results = new ArrayList<>();

        try {
            DAO dao = new DAO();
            try (Connection con = dao.getConnection()) {

                // ▼ 選択肢リストを取得
                List<Integer> entYears = getEntryYears(con, schoolCd);
                List<String> classNums = getClassNums(con, schoolCd);
                List<String> subjects = getSubjects(con, schoolCd);

                request.setAttribute("entYears", entYears);
                request.setAttribute("classNums", classNums);
                request.setAttribute("subjects", subjects);

                // ▼ 条件なしの場合は、空の結果を返す（検索実行しない）
                if ((selectedYear == null || selectedYear.isEmpty()) &&
                    (selectedClass == null || selectedClass.isEmpty()) &&
                    (selectedSubject == null || selectedSubject.isEmpty()) &&
                    (studentNo == null || studentNo.isEmpty())) {

                    request.setAttribute("results", results);
                    RequestDispatcher rd = request.getRequestDispatcher("/disp/grade_list.jsp");
                    rd.forward(request, response);
                    return;
                }

                // ▼ SQL作成（JOIN & 条件付与）
                StringBuilder sql = new StringBuilder(
                    "SELECT s.no AS student_no, s.name AS student_name, s.ent_year, s.class_num, " +
                    "sub.name AS subject_name, t.no AS test_no, t.point " +
                    "FROM STUDENT s " +
                    "LEFT JOIN TEST t ON s.no = t.student_no AND t.school_cd = ? " +
                    "LEFT JOIN SUBJECT sub ON t.subject_cd = sub.cd AND sub.school_cd = ? " +
                    "WHERE s.school_cd = ? AND s.is_attend = TRUE");

                List<Object> params = new ArrayList<>();
                params.add(schoolCd); // for TEST join
                params.add(schoolCd); // for SUBJECT join
                params.add(schoolCd); // for STUDENT

                if (selectedYear != null && !selectedYear.isEmpty()) {
                    sql.append(" AND s.ent_year = ?");
                    params.add(Integer.parseInt(selectedYear));
                }

                if (selectedClass != null && !selectedClass.isEmpty()) {
                    sql.append(" AND s.class_num = ?");
                    params.add(selectedClass);
                }

                if (selectedSubject != null && !selectedSubject.isEmpty()) {
                    sql.append(" AND sub.name = ?");
                    params.add(selectedSubject);
                }

                if (studentNo != null && !studentNo.isEmpty()) {
                    sql.append(" AND s.no = ?");
                    params.add(studentNo);
                }

                sql.append(" ORDER BY s.no, t.no");

                try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                    for (int i = 0; i < params.size(); i++) {
                        Object param = params.get(i);
                        if (param instanceof Integer) {
                            ps.setInt(i + 1, (Integer) param);
                        } else {
                            ps.setString(i + 1, (String) param);
                        }
                    }

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Map<String, Object> row = new HashMap<>();
                            row.put("student_no", rs.getString("student_no"));
                            row.put("student_name", rs.getString("student_name"));
                            row.put("ent_year", rs.getInt("ent_year"));
                            row.put("class_num", rs.getString("class_num"));
                            row.put("subject_name", rs.getString("subject_name"));
                            row.put("test_no", rs.getInt("test_no"));
                            row.put("point", rs.getObject("point")); // null許容
                            results.add(row);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "検索中にエラーが発生しました：" + e.getMessage());
        }

        request.setAttribute("results", results);
        RequestDispatcher rd = request.getRequestDispatcher("/disp/grade_list.jsp");
        rd.forward(request, response);
    }

    // ▼ 入学年度取得
    private List<Integer> getEntryYears(Connection con, String schoolCd) throws Exception {
        List<Integer> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(
            "SELECT DISTINCT ent_year FROM STUDENT WHERE school_cd = ? ORDER BY ent_year")) {
            ps.setString(1, schoolCd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("ent_year"));
                }
            }
        }
        return list;
    }

    // ▼ クラス番号取得
    private List<String> getClassNums(Connection con, String schoolCd) throws Exception {
        List<String> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(
            "SELECT DISTINCT class_num FROM STUDENT WHERE school_cd = ? ORDER BY class_num")) {
            ps.setString(1, schoolCd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("class_num"));
                }
            }
        }
        return list;
    }

 // ▼ 科目取得
    private List<String> getSubject(Connection con, String schoolCd) throws Exception {
        List<String> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(
            "SELECT DISTINCT subject FROM SUBJECT WHERE school_cd = ? ORDER BY subject")) {
            ps.setString(1, schoolCd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("subject"));
                }
            }
        }
        return list;
    }

    // ▼ 科目名取得
    private List<String> getSubjects(Connection con, String schoolCd) throws Exception {
        List<String> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(
            "SELECT DISTINCT name FROM SUBJECT WHERE school_cd = ? ORDER BY name")) {
            ps.setString(1, schoolCd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
            }
        }
        return list;
    }
}