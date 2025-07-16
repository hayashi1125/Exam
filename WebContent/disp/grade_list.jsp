<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<style>
  /* ▼ コンテンツ全体の中央揃え */
  .content-wrapper {
    display: flex;
    justify-content: center;
    margin-top: 30px;
  }

  /* ▼ フォーム全体の白背景＋枠デザイン */
  .search-panel {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 5px #ccc;
    padding: 20px;
    width: 750px;
  }

  /* ▼ 各セクションタイトルの装飾 */
  .section-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 15px;
    border-left: 5px solid #666;
    padding-left: 10px;
  }

  /* ▼ 各入力行（入学年度・クラス・科目など）を横並び＆中央揃え */
  .form-row {
    display: flex;
    justify-content: center;   /* 中央揃え */
    align-items: flex-end;
    gap: 20px;                 /* 各要素の間隔 */
    margin-bottom: 12px;
  }

  /* ▼ 各入力項目のコンテナ（ラベル＋selectやinput） */
  .form-item {
    flex: 1 1 130px;           /* 均等幅で最小150px確保 */
    max-width: 160px;          /* 最大幅を160pxに制限 */
    display: flex;
    flex-direction: column;   /* ラベルと入力を縦並びに */
  }

  /* ▼ ラベルのスタイル */
  .form-item label {
    font-weight: bold;
    margin-bottom: 4px;
  }

  /* ▼ select と input 要素のスタイル（共通） */
  .form-item select,
  .form-item input {
    padding: 5px;
    width: 100%;              /* form-item の幅いっぱいに広げる */
  }

  /* ▼ 検索ボタンのスタイル */
  .form-item button,
  .button-item button {
    display: inline-block;
    border: none;
    border-radius: 2px;
    padding: 5px 8px;
    margin: 3px;
    background-color: #add8e6;
    text-align: center;
    text-decoration: none;
    font-size: 12px;
    cursor: pointer;
  }

  /* ▼ ボタンのみの form-item を小さく固定幅に */
  .button-item {
    flex: 0 0 auto;
    width: auto;
    display: flex;
    align-items: flex-end;
    margin-left: 10px;
  }

  /* ▼ ボタンの hover 効果 */
  .form-item button:hover {
    background-color: #444;
  }

  /* ▼ 補足メッセージの青文字 */
  .info-note {
    color: #007bff;
    font-size: small;
    margin-top: 10px;
  }

  /* ▼ エラーメッセージ（赤文字・太字） */
  .error-message {
    color: red;
    font-weight: bold;
    margin-bottom: 10px;
  }

  /* ▼ 成績テーブルのデザイン */
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }

  /* ▼ テーブル内セルの装飾 */
  table th, table td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: center;
  }
</style>

<div class="content">
  <h2 class="menu-title" style="text-align: center;">成績参照</h2>

  <div class="content-wrapper">
    <div class="search-panel">

      <!-- ▼ エラーメッセージ表示 -->
      <c:if test="${searchType == 'subject' and (empty entYear or empty classNum or empty subject)}">
        <div class="error-message">入学年度とクラスと科目を全て選択してください</div>
      </c:if>

      <c:if test="${searchType == 'subject' and not (empty entYear or empty classNum or empty subject) and empty gradeList}">
        <div class="error-message">科目情報が存在しませんでした</div>
      </c:if>

      <c:if test="${searchType == 'student' and empty gradeList}">
        <div class="error-message">学生情報が存在しませんでした</div>
      </c:if>

      <!-- ▼ フォーム開始 -->
      <form action="${pageContext.request.contextPath}/action/gradelist" method="post">
        <input type="hidden" name="search" value="true">

        <!-- ▼ 科目情報 -->
        <div class="section-title">科目情報</div>
        <div class="form-row">
          <div class="form-item">
            <label>入学年度：</label>
            <select name="entYear">
              <option value="">------</option>
              <c:forEach var="year" items="${entYears}">
                <option value="${year}" ${entYear == year ? "selected" : ""}>${year}</option>
              </c:forEach>
            </select>
          </div>

          <div class="form-item">
            <label>クラス：</label>
            <select name="classNum">
              <option value="">------</option>
              <c:forEach var="cls" items="${classNums}">
                <option value="${cls}" ${classNum == cls ? "selected" : ""}>${cls}</option>
              </c:forEach>
            </select>
          </div>

          <div class="form-item">
            <label>科目：</label>
            <select name="subject">
              <option value="">------</option>
              <c:forEach var="subj" items="${subjects}">
                <option value="${subj}" ${subject == subj ? "selected" : ""}>${subj}</option>
              </c:forEach>
            </select>
          </div>

          <div class="button-item">
            <button type="submit" name="searchType" value="subject">検索</button>
          </div>
        </div>

        <!-- ▼ 学生情報 -->
        <div class="section-title">学生情報</div>
        <div class="form-row">
          <div class="form-item">
            <label>学生番号：</label>
            <input type="text" name="studentId" value="${studentId}" placeholder="学生番号を入力してください">
          </div>
          <div class="button-item">
            <button type="submit" name="searchType" value="student">検索</button>
          </div>
        </div>

        <!-- ▼ 補足 -->
        <div class="info-note">
          科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
        </div>
      </form>

      <!-- ▼ 成績結果表示 -->
      <c:if test="${not empty gradeList}">
        <h3 style="margin-top: 20px;">成績一覧（科目）</h3>
        <table>
          <tr>
            <th>学生番号</th>
            <th>氏名</th>
            <th>科目</th>
            <th>試験回</th>
            <th>点数</th>
          </tr>
          <c:forEach var="g" items="${gradeList}">
            <tr>
              <td>${g.studentId}</td>
              <td>${g.studentName}</td>
              <td>${g.subject}</td>
              <td>${g.examNo}</td>
              <td>${g.score}</td>
            </tr>
          </c:forEach>
        </table>
      </c:if>
      <c:if test="${searchType != null and empty gradeList}">
  <p class="info-note">検索条件に一致する成績が見つかりませんでした。</p>
</c:if>

    </div>
  </div>
</div>

<%@include file="../footer.html" %>
