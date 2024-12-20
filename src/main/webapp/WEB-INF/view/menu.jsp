<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="user" class="model.MVuser" scope="session"/>
<div>
    <ul>
        <li><a href="?page=main">HOME</a></li>
        <li><a href="?page=movies">MOVIES</a></li>
            <% if (user.getPrivileges() > 0) { %>
        <li><a href="?page=favourites">FAVOURITES</a></li>
            <% } %>
            <% if (user.getPrivileges() < 0) { %>
        <li><a href="?page=registration">SIGN UP</a></li>
            <% } %>
            <% if (user.getPrivileges() == 2) { %>
        <li><a href="?page=AdminUsers">ADMINISTRATION</a></li>
            <% } %>

</div>