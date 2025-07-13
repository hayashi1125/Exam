<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.html" %>
<%@ include file="menu.jsp" %>

<h2>成績参照</h2>

<form action="gradelist" method="get">
  <fieldset>
    <legend>科目情報</legend>

    入学年度：
    <select name="entYear">
      <option value="">----</option>
      <option value="2023">2023</option>
      <option value="2024">2024</option>
    </select>

    クラス：
    <select name="className">
      <option value="">----</option>
      <option value="100">100</option>
      <option value="104">104</option>
    </select>

    科目：
    <select name="subject">
      <option value="">----</option>
      <option value="java">java</option>
      <option value="python">python</option>
    </select>

    <button type="submit">検索</button>
  </fieldset>

  <fieldset>
    <legend>学生情報</legend>

    学生番号：
    <input type="text" name="studentId" placeholder="学生番号を入力してください" />

    <button type="submit">検索</button>
  </fieldset>
</form>

<p style="color: #00a;">
  科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
</p>

<c:if test="${not empty gradeList}">
  <table border="1">
    <tr><th>学生番号</th><th>学生名</th><th>科目</th><th>回数</th><th>点数</th></tr>
    <c:forEach var="g" items="${gradeList}">
      <tr>
        <td>${g.studentId}</td>
        <td>${g.studentName}</td>
        <td>${g.subject}</td>
        <td>${g.examNo}</td>
        <td>${g.score}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<%@ include file="../footer.html" %>