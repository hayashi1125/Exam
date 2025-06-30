<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.html" %>
<%@ include file="menu.jsp" %>



<div class="content">
  <h2 class="menu-title">成績管理</h2>
  <form action="GradeList.action" method="get">
    入学年度:
    <select name="admissionYear">
      <option value="">--------</option>
      <option value="2021">2021</option>
      <option value="2022">2022</option>
    </select>

    クラス:
    <select name="className">
      <option value="">--------</option>
      <option value="A">A</option>
      <option value="B">B</option>
    </select>

    科目:
    <select name="subject">
      <option value="">--------</option>
      <option value="math">数学</option>
      <option value="eng">英語</option>
    </select>

    回数:
    <select name="examNo">
      <option value="">--------</option>
      <option value="1">1回</option>
      <option value="2">2回</option>
    </select>

    <button type="submit">検索</button>
  </form>

  <table border="1">
    <tr>
      <th>学生ID</th><th>名前</th><th>得点</th><th>科目</th><th>回数</th>
    </tr>
    <c:forEach var="record" items="${gradeList}">
      <tr>
        <td>${record.studentId}</td>
        <td>${record.studentName}</td>
        <td>${record.score}</td>
        <td>${record.subject}</td>
        <td>${record.examNo}</td>
      </tr>
    </c:forEach>
  </table>
 </div>

<%@include file="../footer.html" %>