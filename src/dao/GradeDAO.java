package dao;

// ▼ 必要なライブラリのインポート
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Grade;  // ▼ 成績情報を格納するBean

public class GradeDAO extends DAO {

    /**
     * 成績データを検索するメソッド
     * @param year 入学年度
     * @param name クラス名
     * @param subject 科目名
     * @param examNo 試験回数
     * @return 該当する成績のリスト
     */
    public List<Grade> searchGrades(String year, String name, String subject, String examNo) throws Exception {
        List<Grade> list = new ArrayList<>();  // 成績のリストを初期化
        Connection con = getConnection();      // DB接続取得

        // ▼ ベースとなるSQL文（WHERE 1=1 を使うことで条件を動的に追加可能）
        String sql = "SELECT * FROM grades WHERE 1=1";
        if (year != null && !year.isEmpty()) sql += " AND admission_year = ?";
        if (name != null && !name.isEmpty()) sql += " AND class = ?";
        if (subject != null && !subject.isEmpty()) sql += " AND subject = ?";
        if (examNo != null && !examNo.isEmpty()) sql += " AND exam_no = ?";

        // ▼ SQL文を準備
        PreparedStatement st = con.prepareStatement(sql);

        // ▼ 条件に応じてパラメータを設定
        int index = 1;
        if (year != null && !year.isEmpty()) st.setString(index++, year);
        if (name != null && !name.isEmpty()) st.setString(index++, name);
        if (subject != null && !subject.isEmpty()) st.setString(index++, subject);
        if (examNo != null && !examNo.isEmpty()) st.setString(index++, examNo);

        // ▼ SQLを実行し、結果を取得
        ResultSet rs = st.executeQuery();

        // ▼ 結果セットからGradeオブジェクトにデータを詰める
        while (rs.next()) {
            Grade g = new Grade();  // 成績インスタンス作成
            g.setStudentId(rs.getString("student_id"));       // 学生番号
            g.setStudentName(rs.getString("student_name"));   // 氏名
            g.setScore(rs.getInt("score"));                   // 得点
            g.setSubject(rs.getString("subject"));            // 科目
            g.setExamNo(rs.getInt("exam_no"));                // 試験回
            list.add(g);  // リストに追加
        }

        // ▼ 使用後はリソースを閉じる
        st.close();
        con.close();

        return list;  // 成績一覧を返す
    }
}
