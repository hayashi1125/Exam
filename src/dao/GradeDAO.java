package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Grade;

public class GradeDAO extends DAO {
    public List<Grade> searchGrades(String year, String className, String subject, String examNo) throws Exception {
        List<Grade> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT * FROM grades WHERE 1=1";
        if (year != null && !year.isEmpty()) sql += " AND admission_year = ?";
        if (className != null && !className.isEmpty()) sql += " AND class = ?";
        if (subject != null && !subject.isEmpty()) sql += " AND subject = ?";
        if (examNo != null && !examNo.isEmpty()) sql += " AND exam_no = ?";

        PreparedStatement st = con.prepareStatement(sql);

        int index = 1;
        if (year != null && !year.isEmpty()) st.setString(index++, year);
        if (className != null && !className.isEmpty()) st.setString(index++, className);
        if (subject != null && !subject.isEmpty()) st.setString(index++, subject);
        if (examNo != null && !examNo.isEmpty()) st.setString(index++, examNo);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Grade g = new Grade();
            g.setStudentId(rs.getString("student_id"));
            g.setStudentName(rs.getString("student_name"));
            g.setScore(rs.getInt("score"));
            g.setSubject(rs.getString("subject"));
            g.setExamNo(rs.getInt("exam_no"));
            list.add(g);
        }

        st.close();
        con.close();
        return list;
    }
}
