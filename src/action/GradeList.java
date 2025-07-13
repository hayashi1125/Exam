package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Grade;
import dao.GradeDAO;

@WebServlet("/action/gradelist") // ★このURLでServletにアクセス可能！
public class GradeList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // GETはPOSTに委譲
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ▼ 検索条件の取得（null・空文字も考慮）
        String entYear   = request.getParameter("entYear");
        String className = request.getParameter("className");
        String subject   = request.getParameter("subject");
        String studentId = request.getParameter("studentId");

        try {
            // ▼ DAO呼び出し
            GradeDAO dao = new GradeDAO();
            List<Grade> gradeList = dao.searchGrades(entYear, className, subject, studentId);

            // ▼ JSPへ渡す
            request.setAttribute("gradeList", gradeList);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "検索中にエラーが発生しました：" + e.getMessage());
        }

        // ▼ JSPへフォワード
        RequestDispatcher rd = request.getRequestDispatcher("/disp/grade_list.jsp");
        rd.forward(request, response);
    }
}
