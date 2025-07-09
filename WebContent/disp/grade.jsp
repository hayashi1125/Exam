<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../header.html" %>
<%@ include file="menu.jsp" %>

<style>
.絞り込み {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 30px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}
.select-group {
  display: flex;
  align-items: center;  /* ラベルとセレクトの高さを揃える */
  gap: 8px;
  min-width: 160px;
}
.select-group label {
  margin: 0;
  font-weight: bold;
  white-space: nowrap; /* ラベルが折り返されないようにする */
}
.select-group select {
  padding: 5px;
  font-size: 14px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
button {
  background-color: #555;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 8px 14px;
  font-size: 14px;
  cursor: pointer;
}
button:hover {
  background-color: #333;
}
table {
  border-collapse: collapse;
  width: 100%;
  margin-top: 10px;
}
th, td {
  border: 1px solid #999;
  padding: 6px;
  text-align: center;
}
th {
  background-color: #eee;
}
</style>

<h2>成績参照</h2>

<c:if test="${not empty error}">
  <div style="color: red;">${error}</div>
</c:if>

<!-- 絞り込みフォーム -->
<form class="絞り込み" action="grade" method="post" style="justify-content: center;">
  <div class="select-group">
    <label>入学年度：</label>
    <select name="ent_year">
      <option value="">--選択--</option>
      <c:forEach var="year" items="${entYears}">
        <option value="${year}" <c:if test="${param.ent_year == year}">selected</c:if>>${year}</option>
      </c:forEach>
    </select>
  </div>

  <div class="select-group">
    <label>クラス番号：</label>
    <select name="class_no">
      <option value="">--選択--</option>
      <c:forEach var="cls" items="${classNums}">
        <option value="${cls}" <c:if test="${param.class_no == cls}">selected</c:if>>${cls}</option>
      </c:forEach>
    </select>
  </div>

  <div class="select-group">
    <label>科目：</label>
    <select name="subject">
      <option value="">--選択--</option>
      <c:forEach var="subj" items="${subjects}">
        <option value="${subj}" <c:if test="${param.subject == subj}">selected</c:if>>${subj}</option>
      </c:forEach>
    </select>
  </div>

  <div class="select-group">
    <label>学生番号：</label>
    <input type="text" name="student_no" value="${fn:escapeXml(param.student_no)}" style="padding: 5px; font-size: 14px; border-radius: 5px; border: 1px solid #ccc; min-width: 160px;">
  </div>

  <div>
    <button type="submit">検索</button>
  </div>
</form>

<!-- 結果表示 -->
<c:if test="${not empty results}">
  <table>
    <thead>
      <tr>
        <th>学生番号</th>
        <th>氏名</th>
        <th>入学年度</th>
        <th>クラス番号</th>
        <th>科目</th>
        <th>テスト回数</th>
        <th>点数</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="row" items="${results}">
        <tr>
          <td>${row.student_no}</td>
          <td>${row.student_name}</td>
          <td>${row.ent_year}</td>
          <td>${row.class_num}</td>
          <td><c:out value="${row.subject_name != null ? row.subject_name : '-'}" /></td>
          <td><c:out value="${row.test_no != 0 ? row.test_no : '-'}" /></td>
          <td><c:out value="${row.point != null ? row.point : '-'}" /></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</c:if>

<c:if test="${empty results}">
  <p>該当する成績データはありません。</p>
</c:if>

<%@ include file="../footer.html" %>