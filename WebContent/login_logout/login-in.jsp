<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>

<style>
.ログイン {
  width: 480px;
  margin: 60px auto;
  padding: 30px 40px;
  background-color: #ffffff;
  /*border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  font-family: "Segoe UI", sans-serif;*/
}

/* タイトル */
.ログイン a {
  display: block;
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 30px;
  text-align: center;
}

/* ラベルと入力欄の横並びレイアウト */
.id_input p,
.pass_input p {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  font-size: 14px;
}

/* ラベル部分 */
.id_input p label,
.pass_input p label {
  width: 120px;
  font-weight: bold;
}

/* 入力欄 */

.id_input input,
.pass_input input {
  flex: 1;
  padding: 6px 10px;
  font-size: 14px;
  border: 1px solid #aaa;
  border-radius: 4px;
}

/* チェックボックスとラベル */
.ログイン input[type="checkbox"] {
  margin-right: 8px;
}

/* ログインボタン */
.ログイン input[type="submit"] {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  background-color: #2c3e50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.ログイン input[type="submit"]:hover {
  background-color: #1a242f;
}
</style>

<div class="ログイン">
  <form action="Login.action" method="post">
    <a>ログイン</a>

    <div class="id_input">
      <p>　　　　　ID
        <input class="ef" type="text" name="id" placeholder="半角でご入力ください" value="${id}" required>
      </p>
    </div>

    <div class="pass_input">
      <p>パスワード
        <input class="ef" type="password" name="password" id="Show" placeholder="30文字以内の半角英数字でご入力ください" maxlength="30" required>
      </p>
    </div>

    <p>
      <input type="checkbox" name="chk_d_ps" onclick="myFunc()">パスワードを表示
    </p>

    <p>
      <input type="submit" name="login" value="ログイン">
    </p>
  </form>
</div>

<script>
  function myFunc() {
    var show = document.getElementById('Show');
    show.type = (show.type === 'password') ? 'text' : 'password';
  }
</script>

<%@include file="../footer.html" %>
