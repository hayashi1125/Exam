<%@page contentType="text/html; charset=UTF-8" %>  <%-- ページの文字コードをUTF-8に設定 --%>
<%@include file="../header.html" %>               <%-- ヘッダー部分をインクルード（共通ヘッダー） --%>
<%@include file="menu.jsp" %>                     <%-- メニュー部分をインクルード（ナビゲーション） --%>

<!-- ▼ メインコンテンツ表示 -->
<div class="content">
  <h2 class="menu-title">成績登録結果</h2>   <!-- ページのタイトル -->

  <p>${message}</p>                          <!-- 登録結果メッセージを表示（コントローラから渡されたメッセージ） -->

  <!-- 成績登録画面へ戻るリンク -->
  <a href="<%= request.getContextPath() %>/action/gradeinsert">成績登録に戻る</a>
</div>

<%@include file="../footer.html" %>           <%-- フッター部分をインクルード（共通フッター） --%>
