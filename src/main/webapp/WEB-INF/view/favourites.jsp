<%@ page import="java.util.List" %>
<%@ page import="model.MVmovie" %>
<%@ page import="database.USERdb" %>
<%@ page import="database.MVdb" %>
<%@ page import="model.MVuser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Favourites</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .title {
            font-size: 36px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
            text-transform: uppercase;
        }

        .movie-gallery {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }

        .movie-poster {
            width: 200px;
            height: auto;
            margin: 10px;
            cursor: pointer;
            transition: transform 0.2s;
        }

        .movie-poster:hover {
            transform: scale(1.05);
        }

        .no-favorites {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="title">Favourites</h1>
    <div class="movie-gallery">
        <%
            MVuser user = (MVuser) session.getAttribute("user");
            if (user != null) {
                USERdb userdb = new USERdb();
                List<Integer> favouriteMovieIds = userdb.selectFavoriteMovieIdsByUserId(user.getId());
                userdb.closeConnection();

                if (favouriteMovieIds != null && !favouriteMovieIds.isEmpty()) {
                    MVdb mvdb = new MVdb();
                    for (int movieId : favouriteMovieIds) {
                        MVmovie movie = mvdb.selectMovieById(movieId);
                        if (movie != null) {
                            byte[] poster = movie.getPoster();
                            if (poster != null && poster.length > 0) {
                                String base64Image = java.util.Base64.getEncoder().encodeToString(poster);
                                String imgSrc = "data:image/jpg;base64," + base64Image;
        %>
        <div class="movie-item">
            <a href="MV?id=<%= movie.getId() %>">
                <img class="movie-poster" src="<%= imgSrc %>" alt="<%= movie.getTitle() %>">
            </a>
        </div>
        <%
                    }
                }
            }
            mvdb.closeConnection();
        } else {
        %>
        <p class="no-favorites">No favourite movies found.</p>
        <%
            }
        } else {
        %>
        <p class="no-favorites">Please log in to view your favourite movies.</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>