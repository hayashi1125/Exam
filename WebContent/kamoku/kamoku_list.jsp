<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.html" %>
<%@ include file="../disp/menu.jsp" %>

<!-- ▼ スタイル定義（ページ内に記述） -->
<style>
  .content {
    width: 80%;
    margin: 0 auto;
    padding: 20px;
  }

  .menu-title {
    background-color: #f0f0f0;
    border-left: 6px solid #3f51b5;
    padding: 10px;
    font-size: 20px;
  }

  .new-entry {
    text-align: right;
    margin: 10px 0;
  }

  .new-entry a {
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
  }

  .subject-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
  }

  .subject-table th, .subject-table td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: center;
  }

  .subject-table th {
    background-color: #e0e0e0;
  }

  .subject-table a {
    text-decoration: none;
    color: #007bff;
  }

  .subject-table a:hover {
    text-decoration: underline;
  }

  .no-data {
    text-align: center;
    font-style: italic;
    color: #666;
  }
</style>

<!-- ▼ 内容 -->
<div class="content">
  <h2 class="menu-title">科目管理</h2>

  <!-- 新規登録 -->
  <div class="new-entry">
    <a href="<%= request.getContextPath() %>/kamoku/subject_create.jsp">新規登録</a>
  </div>

  <!-- 科目一覧テーブル -->
  <table class="subject-table">
    <thead>
      <tr>
        <th>科目コード</th>
        <th>科目名</th>
        <th colspan="2">操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="subject" items="${subjects}">
        <tr>
          <td>${subject.cd}</td>
          <td>${subject.name}</td>
          <td>
          <c:url var="updateUrl" value="/kamoku/subjectupdate.jsp">
            <c:param name="cd" value="${subject.cd}" />
            <c:param name="name" value="${subject.name}" />
          </c:url>

          <a href="${updateUrl}">変更</a>

          </td>
          <td>
            <c:url var="deleteUrl" value="/kamoku/subject_delete.jsp">
            <c:param name="cd" value="${subject.cd}" />
            <c:param name="name" value="${subject.name}" />
          </c:url>

          <a href="${deleteUrl}">削除</a>
          </td>
        </tr>
      </c:forEach>

      <c:if test="${empty subjects}">
        <tr>
          <td colspan="4" class="no-data">表示する科目がありません。</td>
        </tr>
      </c:if>
    </tbody>
  </table>
</div>

<%@ include file="../footer.html" %>