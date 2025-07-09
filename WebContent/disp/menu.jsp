<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  html, body {
    height: 100%;
    margin: 0;
  }
  .container {
    display: table;
    width: 100%;
    height: 100%;
  }
  .row {
    display: table-row;
    height: 100%;
  }
  .menu {
    display: table-cell;
    width: 30%;
    vertical-align: top;
    border-right: 0.5px solid gray;
    padding: 20px;
    box-sizing: border-box;
  }
  .content {
    display: table-cell;
    vertical-align: top;
    padding: 40px;
  }
  .content h2 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
  }
</style>


  <div class="menu">
    <ul style="list-style: none; text-align: left;">
      <li style="margin-bottom: 15px; text-align: left;">
        <a href="<%= request.getContextPath() %>/disp/index.jsp">メニュー</a>
      </li>
      <li style="margin-bottom: 15px; text-align: left;">
        <a href="<%= request.getContextPath() %>/action/studentlist">学生管理</a>
      </li>
      <div style="margin-bottom: 15px; text-align: left;">成績管理</div>
      <li style="margin-bottom: 15px; text-align: left; padding-left: 30px;">
        <a href="<%= request.getContextPath() %>/action/gradeinsert">成績登録</a>
      </li>
      <li style="margin-bottom: 15px; text-align: left; padding-left: 30px;">
        <a href="<%= request.getContextPath() %>/action/grade">成績参照</a>
      </li>
      <li style="margin-bottom: 15px; text-align: left;">
        <a href="<%= request.getContextPath() %>/action/kamoku_list">科目管理</a>
      </li>
    </ul>
  </div>

