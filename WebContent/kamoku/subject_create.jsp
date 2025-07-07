<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="../disp/menu.jsp" %>

<div class="content">
  <h2 class="menu-title">科目登録</h2>

  <form action="<%= request.getContextPath() %>/action/subject_create" method="post">
    <label for="no">科目コード</label><br>
    <input type="text" name="cd" id="cd" maxlength="10" required placeholder="科目コードを入力してください"><br><br>

    <label for="name">科目名</label><br>
    <input type="text" name="name" id="name" maxlength="30" required placeholder="科目名を入力してください"><br><br>


    <input type="submit" value="登録して終了">
    <br><br>
    <a href="<%= request.getContextPath() %>/action/kamoku_list">戻る</a>
  </form>
</div>

<%@include file="../footer.html" %>