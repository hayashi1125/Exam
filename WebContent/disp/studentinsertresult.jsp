<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<div class="content">
  <h2 class="menu-title">登録結果</h2>
  <p>${message}</p>
  <a href="<%= request.getContextPath() %>/action/studentlist">学生一覧に戻る</a>
</div>

<%@include file="../footer.html" %>
