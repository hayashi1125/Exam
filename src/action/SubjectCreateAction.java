package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  // ResultSetのimportを忘れずに

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;  // ログインユーザーのモデルクラス（必要に応じて変更）
import dao.DAO;

@WebServlet("/action/subject_create")
public class SubjectCreateAction extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    HttpSession sess = request.getSession();
    Teacher teacher = (Teacher) sess.getAttribute("teacher");
    if (teacher == null) {
      // ログインしていなければログイン画面へリダイレクト
      response.sendRedirect("login.jsp");
      return;
    }

    String cd = request.getParameter("cd");         // 科目コード
    String name = request.getParameter("name");     // 科目名
    String schoolCd = teacher.getSchool_cd();       // ログインユーザーの学校コード

    String message;

    try (Connection con = new DAO().getConnection()) {
      // ① 重複チェック用SQLを準備
      PreparedStatement checkSt = con.prepareStatement(
        "SELECT COUNT(*) FROM SUBJECT WHERE cd = ? AND school_cd = ?");
      checkSt.setString(1, cd);
      checkSt.setString(2, schoolCd);

      ResultSet rs = checkSt.executeQuery();

      // ② 結果セットの最初の行にカーソルを移動
      rs.next();

      // ③ 重複している場合の処理
      if (rs.getInt(1) > 0) {
        message = "この科目コードはすでに登録されています。";
      } else {
        // ④ 重複していなければINSERT処理
        PreparedStatement st = con.prepareStatement(
          "INSERT INTO SUBJECT (cd, name, school_cd) VALUES (?, ?, ?)");
        st.setString(1, cd);
        st.setString(2, name);
        st.setString(3, schoolCd);

        int rows = st.executeUpdate();
        message = (rows > 0) ? "登録が完了しました。" : "登録に失敗しました。";
        st.close();
      }
      rs.close();
      checkSt.close();

    } catch (Exception e) {
      e.printStackTrace();
      message = "登録中にエラーが発生しました。<br>" + e.getMessage();
    }

    // メッセージをセッションに保存して、リダイレクト先のJSPで表示させる
    sess.setAttribute("message", message);

    // 登録結果ページにリダイレクト
    response.sendRedirect(request.getContextPath() + "/kamoku/subject_create_done.jsp");
  }
}