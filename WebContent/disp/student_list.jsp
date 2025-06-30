<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<div class="content">
  <h2 class="menu-title">学生管理一覧</h2>

  <!-- 絞り込みフォーム -->
  <form action="studentlist" method="get">
    <label>入学年度：</label>
    <select name="ent_year">
      <option value="">--選択--</option>
      <c:forEach var="year" items="${entYears}">
        <option value="${year}" <c:if test="${param.ent_year == year}">selected</c:if>>${year}</option>
      </c:forEach>
    </select>

    <label>クラス：</label>
    <select name="class_no">
      <option value="">--選択--</option>
      <c:forEach var="cls" items="${classNums}">
        <option value="${cls}" <c:if test="${param.class_no == cls}">selected</c:if>>${cls}</option>
      </c:forEach>
    </select>

    <label>
      <input type="checkbox" name="is_attend" value="1" <c:if test="${param.is_attend == '1'}">checked</c:if>> 在学中
    </label>

    <button type="submit">絞り込み</button>
  </form>

  <!-- 新規登録 -->
  <div style="margin-top: 10px;">
    <a href="<%= request.getContextPath() %>/disp/studentinsertform">新規登録</a>
  </div>

  <!-- 件数表示 -->
  <div style="margin-top: 10px;">
    検索結果：${students.size()} 件
  </div>

  <!-- 学生一覧 -->
  <table border="1" style="margin: 10px auto; border-collapse: collapse;">
    <thead>
      <tr>
        <th>学生番号</th>
        <th>氏名</th>
        <th>入学年度</th>
        <th>クラス</th>
        <th>在学中</th>
        <th>変更</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="student" items="${students}">
        <tr>
          <td>${student.no}</td>
          <td>${student.name}</td>
          <td>${student.ent_year}</td>
          <td>${student.class_num}</td>
          <td>
            <c:choose>
              <c:when test="${student.is_attend == true}">〇</c:when>
              <c:otherwise>×</c:otherwise>
            </c:choose>
          </td>
          <td><a href="<%= request.getContextPath() %>/disp/studentupdateform?no=${student.no}">変更</a></td>
        </tr>
      </c:forEach>
      <c:if test="${empty students}">
        <tr><td colspan="6">該当する学生情報がありません。</td></tr>
      </c:if>
    </tbody>
  </table>
</div>

<%@include file="../footer.html" %>
