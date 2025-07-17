<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../disp/menu.jsp" %>

<div class="content">
  <h2 class="menu-title">成績削除確認</h2>

  <p>以下の成績を削除してもよろしいですか？</p>

  <ul>
    <li><strong>科目：</strong> ${param.subject}</li>
    <li><strong>試験回：</strong> ${param.examNo}</li>
  </ul>

  <form action="<%= request.getContextPath() %>/action/gradedelete" method="post">
    <input type="hidden" name="subject" value="${param.subject}">
    <input type="hidden" name="examNo" value="${param.examNo}">
    <input type="submit" value="削除する" style="background-color: red; color: white; padding: 6px 12px;">
  </form>

  <br>
  <a href="<%= request.getContextPath() %>/action/gradelist">戻る</a>
</div>

<%@include file="../footer.html" %>
