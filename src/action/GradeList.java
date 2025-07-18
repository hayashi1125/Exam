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

@WebServlet("/action/gradelist")
public class GradeList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String entYear   = request.getParameter("entYear");
        String classNum  = request.getParameter("classNum");
        String subject   = request.getParameter("subject");
        String studentId = request.getParameter("studentId");
        String searchType = request.getParameter("searchType");

        try {
            GradeDAO dao = new GradeDAO();
            List<Grade> gradeList = null;

            if ("subject".equals(searchType)) {
                // 科目情報検索
                if (entYear != null && !entYear.isEmpty()
                        && classNum != null && !classNum.isEmpty()
                        && subject != null && !subject.isEmpty()) {
                    gradeList = dao.findBySubject(entYear, classNum, subject);
                } else {
                    gradeList = null;
                }
            } else if ("student".equals(searchType)) {
                // 学生番号検索
                if (studentId != null && !studentId.isEmpty()) {
                    gradeList = dao.findByStudentId(studentId);
                } else {
                    gradeList = null;
                }
            } else if ("all".equals(searchType)) {
                // 全件表示
                gradeList = dao.findAll();
            }

            // 選択リストおよび検索結果をセット
            request.setAttribute("entYear", entYear);
            request.setAttribute("classNum", classNum);
            request.setAttribute("subject", subject);
            request.setAttribute("studentId", studentId);
            request.setAttribute("searchType", searchType);
            request.setAttribute("gradeList", gradeList);
            request.setAttribute("entYears", dao.getEntYears());
            request.setAttribute("classNums", dao.getClassNums());
            request.setAttribute("subjects", dao.getSubjects());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "検索中にエラーが発生しました：" + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/disp/grade_list.jsp");
        rd.forward(request, response);
    }
}
