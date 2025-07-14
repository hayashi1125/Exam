package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Grade;

public class GradeDAO extends DAO {

    // 科目情報で成績検索
    public List<Grade> findBySubject(String entYear, String classNum, String subjectName) throws Exception {
        List<Grade> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT s.NO AS student_id, s.NAME AS student_name, sub.NAME AS subject, " +
                     "t.NO AS exam_no, t.POINT AS score " +
                     "FROM STUDENT s " +
                     "JOIN TEST t ON s.NO = t.STUDENT_NO " +
                     "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD AND t.SCHOOL_CD = sub.SCHOOL_CD " +
                     "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND sub.NAME = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, entYear);
        st.setString(2, classNum);
        st.setString(3, subjectName);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Grade g = new Grade();
            g.setStudentId(rs.getString("student_id"));
            g.setStudentName(rs.getString("student_name"));
            g.setSubject(rs.getString("subject"));
            g.setExamNo(rs.getInt("exam_no"));
            g.setScore(rs.getInt("score"));
            list.add(g);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // 学生番号で成績検索
    public List<Grade> findByStudentId(String studentId) throws Exception {
        List<Grade> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT s.NO AS student_id, s.NAME AS student_name, sub.NAME AS subject, " +
                     "t.NO AS exam_no, t.POINT AS score " +
                     "FROM STUDENT s " +
                     "JOIN TEST t ON s.NO = t.STUDENT_NO " +
                     "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD AND t.SCHOOL_CD = sub.SCHOOL_CD " +
                     "WHERE s.NO = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, studentId);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Grade g = new Grade();
            g.setStudentId(rs.getString("student_id"));
            g.setStudentName(rs.getString("student_name"));
            g.setSubject(rs.getString("subject"));
            g.setExamNo(rs.getInt("exam_no"));
            g.setScore(rs.getInt("score"));
            list.add(g);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // 入学年度一覧を取得
    public List<String> getEntYears() throws Exception {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT DISTINCT ENT_YEAR FROM STUDENT ORDER BY ENT_YEAR DESC";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            list.add(rs.getString("ENT_YEAR"));
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // クラス一覧を取得
    public List<String> getClassNums() throws Exception {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT DISTINCT CLASS_NUM FROM STUDENT ORDER BY CLASS_NUM";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            list.add(rs.getString("CLASS_NUM"));
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // 科目一覧を取得
    public List<String> getSubjects() throws Exception {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT DISTINCT NAME FROM SUBJECT ORDER BY NAME";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            list.add(rs.getString("NAME"));
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }
}
