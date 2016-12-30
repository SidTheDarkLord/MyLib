<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
  <head>
    <title>Онлайн библиотека::Вход</title>
    <link href="CSS/style_index.css" rel="stylesheet" type="text/css">
  </head>

  <body>
  <div class="main">

    <div class="content">
      <p class="title"><span class="text"><img src="images/books.jpg" width="240" height="120" hspace="10" vspace="5" align="middle"></span></p>
      <p class="title">Онлайн библиотека</p>
      <p class="text">Добро пожаловать в онлайн библиотеку, где Вы сможете найти книгу на любой вкус. Доступны функции поиска, просмотра, сортировки и многое другое.</p>
      <p class="text">Проект находится в разработке, поэтому дизайн и функционал постоянно дорабатываются.</p>
      <p class="text">По всем вопросам обращайтесь по адресу: <a href="mailto:support@testlibrary.com">support@testlibrary.com</a> </p>
    </div>

    <div class="login_div">
      <p class="title">Введите логин</p>
      <form class="login_form" name="username" action="pages/main.jsp" method="post">
        Логин: <input type="text" name="username" value="" size="20">
        <input type="submit" value="Войти">
      </form>
    </div>

      <div class="footer">
        Разработчик: Андрюша, 2016г.
      </div>

  </div>
  </body>
</html>
