<%@ page import="model.MVmovie" %>
<%@ page import="model.MVuser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Details - MDB</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }

        h1 {
            text-align: center;
            padding: 20px 0;
            background-color: #343a40;
            color: white;
            margin: 0 0 20px 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .movie-details {
            display: flex;
            flex-wrap: wrap;
            align-items: flex-start;
            gap: 20px;
        }

        .movie-poster {
            width: 200px;
            height: auto;
        }

        .movie-info {
            flex: 1;
        }

        .movie-info h2 {
            font-weight: bold;
            margin: 0 0 10px 0;
            font-size: 24px;
            color: #007bff;
        }

        .movie-info p {
            margin: 5px 0;
            font-size: 16px;
            line-height: 1.6;
        }
        .btn-edit {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s, transform 0.3s;
            display: inline-block;
            text-align: center;
            line-height: 1.5;
        }

        .btn-edit:hover {
            background-color: #218838;
            transform: translateY(-2px);
        }

        .btn-gray {
            background-color: #6c757d;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .btn-gray:hover {
            background-color: #5a6268;
        }

        .movie-info p b {
            color: #555;
        }

        .actions {
            margin-top: 20px;
        }

        .actions form,
        .actions a {
            display: inline-block;
            margin-right: 10px;
        }

        .actions button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .actions button:hover {
            background-color: #0056b3;
        }
        .btn-edit {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s, transform 0.3s;
            display: inline-block;
            text-align: center;
            line-height: 1.5;
        }

        .btn-edit:hover {
            background-color: #218838;
            transform: translateY(-2px);
        }

        .actions a {
            background-color: #6c757d;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .actions a:hover {
            background-color: #5a6268;
        }

        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            .movie-details {
                flex-direction: column;
                align-items: center;
            }

            .movie-poster {
                width: 150px;
            }

            .movie-info h2 {
                font-size: 20px;
            }

            .actions button,
            .actions a {
                padding: 8px 16px;
            }
        }
    </style>

</head>
<body>
<header>
    <div class="navbar">
        <div id="menu">
            <div class="logo">MDB</div>
            <jsp:include page="/WEB-INF/view/menu.jsp"/>
        </div>
    </div>
</header>
<div class="container">
    <div class="movie-details">
        <%
            MVmovie movie = (MVmovie) request.getAttribute("movie");
            if (movie != null) {
        %>
        <div>
            <img class="movie-poster" src="data:image/jpg;base64,<%= java.util.Base64.getEncoder().encodeToString(movie.getPoster()) %>" alt="Movie Poster">
        </div>
        <div class="movie-info">
            <h2><%= movie.getTitle() %></h2>
            <p><b>Producer:</b> <%= movie.getProducer() %></p>
            <p><b>Actors:</b> <%= movie.getActors() %></p>
            <p><b>Summary:</b> <%= movie.getSummary() %></p>
            <p><b>Release Year:</b> <%= movie.getReleaseYear() %></p>
            <p><b>Genre:</b> <%= movie.getGenre() %></p>
            <%
                MVuser user = (MVuser) request.getSession().getAttribute("user");
                if (user != null && user.getPrivileges() == 2) { // Check if user is admin
            %>
            <a href="MV?id1=<%= movie.getId() %>" class="btn-edit">Edit</a>
            <% } %>
        </div>
        <% } else { %>
        <p>Movie details not available.</p>
        <% } %>
    </div>

    <div class="actions">
        <% MVuser user = (MVuser) request.getSession().getAttribute("user"); %>
        <% if (user != null && user.getPrivileges() > 0) { %>
        <form action="MV" method="post">
            <input type="hidden" name="action" value="addFav">
            <input type="hidden" name="movieId" value="<%= movie != null ? movie.getId() : "" %>">
            <button type="submit">Add to Favourites</button>
        </form>
        <% } %>
        <form action="MV" method="post">
            <input type="hidden" name="action" value="backMovies">
            <button type="submit" style="background-color: #6c757d; color: white; padding: 10px 20px; border-radius: 4px; border: none; cursor: pointer; transition: background-color 0.3s;">Back to Movies</button>
        </form>
    </div>

</div>
</body>
</html>