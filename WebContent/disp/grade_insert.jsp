<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<!-- align-items: center;   /* ラベルとセレクトの縦位置揃え */ -->
<!-- ▼ スタイル（CSS）設定 -->
<style>
.絞り込み {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 30px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}
.select-group {
  display: flex;
  align-items: center;  /* ラベルとセレクトの高さを揃える */
  gap: 8px;
  min-width: 160px;
}

.select-group label {
  margin: 0;
  font-weight: bold;
  white-space: nowrap; /* ラベルが折り返されないようにする */
}

.select-group select {
  padding: 5px;
  font-size: 14px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.button-group {
  margin-top: 10px;
}
button {
  background-color: #555;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 8px 14px;
  font-size: 14px;
  cursor: pointer;
}
button:hover {
  background-color: #333;
}
table {
  width: 80%;
  margin: 10px auto;
  border-collapse: collapse;
  font-size: 14px;
}
table th, table td {
  padding: 10px;
  text-align: center;
}
table th {
  background-color: #f0f0f0;
  font-weight: bold;
}
input[type="number"] {
  width: 80px;
  padding: 5px;
  text-align: right;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.error-msg {
  color: red;
  font-size: 12px;
  margin-left: 5px;
}
</style>


<!-- ▼ メインコンテンツ -->
<div class="content">
  <h2 class="menu-title">成績登録</h2>

  <!-- ▼ 絞り込みフォーム（GETリクエストで条件指定） -->
  <form class="絞り込み" action="gradeinsert" method="get" style="justify-content: center;">
    <!-- ▼ 入学年度選択 -->
    <div class="select-group">
      <label>入学年度：</label>
      <select name="ent_year" >
        <option value="">--選択--</option>
        <c:forEach var="year" items="${entYears}">
          <option value="${year}" <c:if test="${param.ent_year == year}">selected</c:if>>${year}</option>
        </c:forEach>
      </select>
    </div>

    <!-- ▼ クラス選択 -->
    <div class="select-group">
      <label>クラス：</label>
      <select name="class_no" >
        <option value="">--選択--</option>
        <c:forEach var="cls" items="${classNums}">
          <option value="${cls}" <c:if test="${param.class_no == cls}">selected</c:if>>${cls}</option>
        </c:forEach>
      </select>
    </div>

    <!-- ▼ 科目選択 -->
    <div class="select-group">
      <label>科目：</label>
      <select name="subject">
        <option value="">--選択--</option>
        <c:forEach var="subj" items="${subjects}">
          <option value="${subj}" <c:if test="${param.subject == subj}">selected</c:if>>${subj}</option>
        </c:forEach>
      </select>
    </div>

    <!-- ▼ 回数選択 -->
    <div class="select-group">
      <label>回数：</label>
      <select name="exam_round">
        <option value="">--選択--</option>
        <c:forEach var="round" items="${examRounds}">
          <option value="${round}" <c:if test="${param.exam_round == round}">selected</c:if>>第${round}回</option>
        </c:forEach>
      </select>
    </div>

    <!-- ▼ 検索ボタン -->
    <div class="button-group">
      <button type="submit" name="search">検索</button>
    </div>
  </form>

  <!-- ▼ 学生が存在する場合の表示 -->
  <c:if test="${not empty students}">
    <!-- ▼ 成績入力用のPOSTフォーム -->
    <form action="gradeinsert" method="post">

      <!-- ▼ 件数表示 -->
      <div style="margin-top: 10px;">
        対象学生：${students.size()} 名
      </div>

      <!-- ▼ 科目と回数の表示（省略時非表示） -->
      <c:if test="${not empty param.subject && not empty param.exam_round}">
        <p>科目：${param.subject}（第${param.exam_round}回）</p>
      </c:if>

      <!-- ▼ フォーム送信用のhidden項目 -->
      <input type="hidden" name="subject" value="${param.subject}" />
      <input type="hidden" name="exam_round" value="${param.exam_round}" />
      <input type="hidden" name="class_no" value="${param.class_no}" />
      <input type="hidden" name="ent_year" value="${param.ent_year}" />

      <!-- ▼ 成績入力テーブル -->
      <table border="1" style="margin: 10px auto; border-collapse: collapse;">
        <thead>
          <tr>
            <th>学生番号</th>
            <th>氏名</th>
            <th>クラス</th>
            <th>点数</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="student" items="${students}">
            <tr>
              <td>
                ${student.no}
                <!-- ▼ 各学生番号をhiddenで送信 -->
                <input type="hidden" name="student_no" value="${student.no}" />
              </td>
              <td>${student.name}</td>
              <td>${student.class_num}</td>
              <td>
                <!-- ▼ 点数入力欄（0〜100の間、初期値あり） -->
                <input type="number" name="point_${student.no}" min="0" max="100"
                 value="${pointMap[student.no]}">
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- ▼ 登録ボタン -->
      <button type="submit">登録</button>
    </form>
  </c:if>

  <!-- ▼ 学生が0件だった場合のメッセージ -->
  <c:if test="${empty students && param.ent_year != null}">
    <div style="margin-top: 10px; color: red;">該当する学生がいません。</div>
  </c:if>
</div>

<!-- ▼ フッター読み込み -->
<%@include file="../footer.html" %>
