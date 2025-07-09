<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="../disp/menu.jsp" %>

<div class="content">
  <h2 class="menu-title">科目情報変更</h2>

  <form action="<%= request.getContextPath() %>/action/subjectupdate" method="post">

    <label>科目コード</label><br>
    <div>${param.cd}</div>
    <input type="hidden" name="cd" value="${param.cd}" required placeholder="科目コードを入力してください">
    <br>

    <label for="name">科目</label><br>
    <input type="text" id="name" name="name" value="${param.name}" maxlength="30" required placeholder="科目を入力してください"><br><br>

    <input type="submit" value="変更">
    <br><br>
    <a href="<%= request.getContextPath() %>/action/subjectlist">戻る</a>

  </form>
</div>

<%@include file="../footer.html" %>