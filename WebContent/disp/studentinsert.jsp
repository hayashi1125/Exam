<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<div class="content">
  <h2 class="menu-title">学生登録</h2>

  <form action="<%= request.getContextPath() %>/action/studentinsert" method="post">
    <label for="no">学生番号</label><br>
    <input type="text" name="no" id="no" maxlength="10" required placeholder="学生番号を入力してください"><br><br>

    <label for="name">氏名</label><br>
    <input type="text" name="name" id="name" maxlength="30" required placeholder="氏名を入力してください"><br><br>

    <label for="ent_year">入学年度</label><br>
    <select name="ent_year" id="ent_year" required>
      <option value="">--選択してください--</option>
      <c:forEach var="year" items="${ent_year_list}">
        <option value="${year}" <c:if test="${param.ent_year == year}">selected</c:if>>${year}</option>
      </c:forEach>
    </select><br><br>

    <label for="class_num">クラス</label><br>
    <select name="class_num" id="class_num" required>
      <option value="">--選択してください--</option>
      <c:forEach var="cls" items="${class_num_list}">
        <option value="${cls}" <c:if test="${param.class_num == cls}">selected</c:if>>${cls}</option>
      </c:forEach>
    </select><br><br>

    <label>在学中</label><br>
    <input type="radio" name="is_attend" value="1" checked> 在学中
    <input type="radio" name="is_attend" value="0"> 卒業<br><br>

    <input type="submit" value="登録して終了">
    <br><br>
    <a href="<%= request.getContextPath() %>/action/studentlist">戻る</a>
  </form>
</div>

<%@include file="../footer.html" %>
