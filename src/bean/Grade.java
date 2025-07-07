package bean;

// ▼ 成績情報を表す JavaBean
public class Grade {

    // ▼ 学生番号
    private String studentId;

    // ▼ 学生氏名
    private String studentName;

    // ▼ 科目名
    private String subject;

    // ▼ 試験回数（例：第1回）
    private int examNo;

    // ▼ 得点
    private int score;

    // ▼ 学生番号の取得
    public String getStudentId() {
        return studentId;
    }

    // ▼ 学生番号の設定
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // ▼ 学生氏名の取得
    public String getStudentName() {
        return studentName;
    }

    // ▼ 学生氏名の設定
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // ▼ 科目名の取得
    public String getSubject() {
        return subject;
    }

    // ▼ 科目名の設定
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // ▼ 試験回数の取得
    public int getExamNo() {
        return examNo;
    }

    // ▼ 試験回数の設定
    public void setExamNo(int examNo) {
        this.examNo = examNo;
    }

    // ▼ 得点の取得
    public int getScore() {
        return score;
    }

    // ▼ 得点の設定
    public void setScore(int score) {
        this.score = score;
    }
}
