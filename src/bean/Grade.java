package bean;

public class Grade {
    private String studentId;
    private String studentName;
    private int score;
    private String subject;
    private int examNo;

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setExamNo(int examNo) {
        this.examNo = examNo;
    }

    // 必要に応じて getter も定義
}