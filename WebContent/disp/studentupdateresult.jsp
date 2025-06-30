<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<div class="content">
  <h2 class="menu-title">更新結果</h2>
  <p>${message}</p>
  <a href="<%= request.getContextPath() %>/action/studentlist">一覧へ戻る</a>
</div>
<%@include file="../footer.html" %>
