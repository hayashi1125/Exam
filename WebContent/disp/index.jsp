<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>

<%@ include file="menu.jsp" %>

<div class="content">
	<h2 class="menu-title">メニュー</h2>

  <div style="margin-top: 30px;">
   <div class="card-container">

    <ul class="card student">
      <li style="margin-bottom: 15px;">
        <a href="<%= request.getContextPath() %>/action/studentlist">学生管理</a>
      </li>
    </ul>
    <ul class="card score">
      <div style="margin-bottom: 15px;">成績管理</div>
      <li style="margin-bottom: 15px;">
        <a href="<%= request.getContextPath() %>/action/gradeinsert">成績登録</a>
      </li>
      <li style="margin-bottom: 15px;">
        <a href="<%= request.getContextPath() %>/action/grade">成績参照</a>
      </li>
    </ul>
    <ul class="card subject">
      <li style="margin-bottom: 15px;">
        <a href="<%= request.getContextPath() %>/action/kamoku_list">科目管理</a>
      </li>
    </ul>
   </div>
  </div>

</div>



<%@include file="../footer.html" %>
