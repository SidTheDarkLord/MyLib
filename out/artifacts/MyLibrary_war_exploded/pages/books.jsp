<%@ page import="ru.web.beans.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.web.enums.SearchType" %>

<%@include file="../WEB-INF/jspf/left_menu.jspf"%>
<%@include file="../WEB-INF/jspf/letters.jspf"%>

<jsp:useBean id="bookList" class="ru.web.beans.BookList" scope="page"/>

<%
    //заполнение списка книг согласно параметрам выбора
    ArrayList<Book> list = null;
    if(request.getParameter("genre_id") != null) {
        long genreId = Long.valueOf(request.getParameter("genre_id"));
        list = bookList.getBooksByGenre(genreId);
    } else if (request.getParameter("letter") != null) {
        String letter = new String(request.getParameter("letter").getBytes("ISO8859-1"), "UTF-8");
        list = bookList.getBooksByLetter(letter);
    } else if(request.getParameter("search_string") != null) {
        String searchStr = new String(request.getParameter("search_string").getBytes("ISO8859-1"), "UTF-8");
        SearchType type = SearchType.TYTLE;
        if(new String(request.getParameter("search_option").getBytes("ISO8859-1"), "UTF-8").equals("Автор")) {
            type = SearchType.AUTHOR;
        }
        //отображение списка книг
        if(searchStr != null && !searchStr.trim().equals("")) {
            list = bookList.getBooksBySearch(searchStr, type);
        }
    }
%>

<div class="book_list">

    <h5 style="text-align: left; margin-bottom: 5px;">Найдено книг: <%=list.size()%></h5>
    <%
        session.setAttribute("currentBookList", list);
        for (Book book : list) {
    %>
    <div class="book_info">
        <div class="book_title">
            <p><a href="<%=request.getContextPath()%>/PdfContent?index=<%=list.indexOf(book)%>"><%=book.getName()%></a></p>
        </div>
        <div class="book_image">
            <a href="<%=request.getContextPath()%>/PdfContent?index=<%=list.indexOf(book)%>">
                <img src="<%=request.getContextPath()%>/ShowImage?index=<%=list.indexOf(book)%>"
                 height="250" width="190" alt="Обложка"></a>
        </div>
        <div class="book_details">
            <br><strong>Автор: </strong><%=book.getAuthor()%>
            <br><strong>Издательство: </strong><%=book.getPublisher()%>
            <br><strong>Год издания: </strong><%=book.getPublishDate()%>
            <br><strong>Кол-во страниц: </strong><%=book.getPageCount()%>
            <br><strong>ISBN: </strong><%=book.getIsbn()%>
            <p style="margin: 10px;"><a href="<%=request.getContextPath()%>/PdfContent?index=<%=list.indexOf(book)%>">Читать</a></p>
        </div>
    </div>
    <%}%>

</div>







