<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<div class="content">
  <h2 class="menu-title">学生情報変更</h2>

  <form action="<%= request.getContextPath() %>/action/studentupdate" method="post">

    <!-- 学生番号（表示のみ） -->
    <label>学生番号</label><br>
    <div>${student.no}</div>
    <input type="hidden" name="no" value="${student.no}">
    <br><br>

    <!-- 入学年度（表示のみ） -->
    <label>入学年度</label><br>
    <div>${student.ent_year}</div>
    <input type="hidden" name="ent_year" value="${student.ent_year}">
    <br><br>

    <!-- 氏名（編集可） -->
    <label for="name">氏名</label><br>
    <input type="text" id="name" name="name" value="${student.name}" maxlength="30" required><br><br>

    <!-- クラス（select） -->
    <label for="class_num">クラス</label><br>
    <select name="class_num" id="class_num" required>
      <option value="">--選択してください--</option>
      <c:forEach var="cls" items="${class_num_list}">
        <option value="${cls}" <c:if test="${cls == student.class_num}">selected</c:if>>${cls}</option>
      </c:forEach>
    </select><br><br>

    <!-- 在学中チェック -->
    <label></label><br>
    <input type="checkbox" name="is_attend" value="1" <c:if test="${student.is_attend}">checked</c:if>> 在学中<br><br>

    <!-- ボタン -->
    <input type="submit" value="変更">
    <br><br>
    <a href="<%= request.getContextPath() %>/action/studentlist">戻る</a>

  </form>
</div>

<%@include file="../footer.html" %>
