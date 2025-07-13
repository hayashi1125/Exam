<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../disp/menu.jsp" %>

<div class="content">
  <h2 class="menu-title">科目情報削除</h2>

  <p>「選択した科目」を削除してもよろしいですか</p>  <!-- 「<strong>${subject.name}(${subject.cd})</strong>」 -->
  <p>${param.cd}</p>
  <p>${param.name}</p>
  <form action="<%= request.getContextPath() %>/action/subjectdelete" method="post">
    <input type="hidden" name="cd" value="${param.cd}">
    <input type="submit" value="削除する">
  </form>

  <br>
  <a href="<%= request.getContextPath() %>/action/subjectlist">戻る</a>
</div>

<%@include file="../footer.html" %>