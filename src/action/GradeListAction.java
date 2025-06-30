package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Grade;
import dao.GradeDAO;

public class GradeListAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String year = request.getParameter("admissionYear");
        String className = request.getParameter("className");
        String subject = request.getParameter("subject");
        String examNo = request.getParameter("examNo");

        try {
            GradeDAO dao = new GradeDAO();
            List<Grade> gradeList = dao.searchGrades(year, className, subject, examNo);
            request.setAttribute("gradeList", gradeList);
            request.getRequestDispatcher("/gradeList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // ログ出力（本番ではログフレームワーク推奨）
            throw new ServletException("成績情報の取得中にエラーが発生しました。", e);
        }
    }
}
