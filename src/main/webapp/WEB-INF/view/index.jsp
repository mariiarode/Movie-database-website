<%@ page import="model.Tools, model.MVuser" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MDB - Movie Database</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<header>
    <div class="navbar">
        <div id="menu">
            <div class="logo">MDB</div>
            <jsp:include page="/WEB-INF/view/menu.jsp"/>
        </div>

        <div class="login-form">
            <jsp:useBean id="user" class="model.MVuser" scope="session" />
            <%
                if (user.getPrivileges()>0) {
            %>
            <form action="MV" method="post">
                <input type="hidden" name="action" value="logout">
                <button type="submit">Logout</button>
            </form>
            <%
            } else {
            %>
            <form action="MV" method="post">
                <input type="hidden" name="action" value="login">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>

            </form>
            <%
                }
            %>
        </div>
    </div>
</header>

<%
    String pageName = request.getParameter("page");
    if (user != null && user.getPrivileges() == 1)
        pageName = Tools.parsePage(pageName, "main;movies;favourites;movieDetails;");
    if (user != null && user.getPrivileges() == 2)
        pageName = Tools.parsePage(pageName, "main;movies;favourites;movieDetails;AdminUsers");
    else
        pageName = Tools.parsePage(pageName, "main;movies;movieDetails;registration;favourites");
%>

<div id="content">
    <jsp:include page="/WEB-INF/view/content.jsp">
        <jsp:param name="what_page" value="<%= pageName %>"/>
    </jsp:include>
</div>

</body>
</html>
