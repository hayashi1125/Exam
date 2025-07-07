<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../disp/menu.jsp" %>

<%
  String message = (String) session.getAttribute("message");
  session.removeAttribute("message");
%>

<div class="content">
  <h2 class="menu-title">登録結果</h2>
  <p><%= message != null ? message : "結果メッセージがありません。" %></p>
  <a href="<%= request.getContextPath() %>/action/kamoku_list">科目一覧に戻る</a>
</div>

<%@include file="../footer.html" %>