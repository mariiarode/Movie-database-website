<%@ page import="java.util.List" %>
<%@ page import="model.MVmovie" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .sort-options {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            margin-bottom: 20px;
        }

        .sort-options label,
        .sort-options select,
        .sort-options button {
            font-size: 16px;
            margin: 0 5px;
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .sort-options select,
        .sort-options button {
            background-color: #f0f0f0;
            cursor: pointer;
        }

        .sort-options button {
            background-color: #007bff;
            color: white;
            border: none;
            transition: background-color 0.3s;
        }

        .sort-options button:hover {
            background-color: #0056b3;
        }

        .movie-gallery {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }

        .movie-item {
            text-align: center;
            margin: 10px;
        }

        .movie-poster {
            width: 200px;
            height: 300px;
            cursor: pointer;
            transition: transform 0.2s;
            object-fit: cover;
            border-radius: 8px;
        }

        .movie-poster:hover {
            transform: scale(1.05);
        }

        .movie-title {
            margin: 10px 0;
            font-size: 18px;
            color: #555;
        }

        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            .sort-options {
                justify-content: center;
                flex-wrap: wrap;
            }

            .movie-gallery {
                gap: 10px;
            }

            .movie-poster {
                width: 150px;
                height: 225px;
            }

            .movie-title {
                font-size: 16px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="sort-options">
        <label for="sort-by">Sort By:</label>
        <select id="sort-by">
            <option value="title">Title</option>
            <option value="producer">Producer</option>
            <option value="genre">Genre</option>
            <option value="releaseYear">Release Year</option>
        </select>
        <button onclick="sortMovies()">Sort</button>
    </div>
    <div class="movie-gallery">
        <%
            List<MVmovie> movies = (List<MVmovie>) request.getAttribute("movies");

            if (movies != null) {
                for (MVmovie movie : movies) {
                    byte[] poster = movie.getPoster();
                    if (poster != null && poster.length > 0) {
                        String base64Image = java.util.Base64.getEncoder().encodeToString(poster);
                        String imgSrc = "data:image/jpg;base64," + base64Image;
        %>
        <div class="movie-item">
            <a href="MV?id=<%= movie.getId() %>" data-genre="<%= movie.getGenre() %>" data-release-year="<%= movie.getReleaseYear() %>">
                <img class="movie-poster" src="<%= imgSrc %>" alt="<%= movie.getTitle() %> - <%= movie.getProducer() %>">
            </a>
        </div>
        <%
                    }
                }
            }
        %>
    </div>
</div>
<script>
    function sortMovies() {
        var sortBy = document.getElementById("sort-by").value;
        var moviesContainer = document.querySelector(".movie-gallery");
        var movies = Array.from(moviesContainer.querySelectorAll("a"));

        movies.sort(function(a, b) {
            var valueA, valueB;
            if (sortBy === "title" || sortBy === "producer") {
                valueA = a.querySelector("img").getAttribute("alt");
                valueB = b.querySelector("img").getAttribute("alt");
            } else if (sortBy === "genre") {
                valueA = a.getAttribute("data-genre");
                valueB = b.getAttribute("data-genre");
            } else if (sortBy === "releaseYear") {
                valueA = parseInt(a.getAttribute("data-release-year"));
                valueB = parseInt(b.getAttribute("data-release-year"));
            }

            if (typeof valueA === 'string') {
                valueA = valueA.toLowerCase();
                valueB = valueB.toLowerCase();
            }

            if (valueA < valueB) {
                return -1;
            }
            if (valueA > valueB) {
                return 1;
            }
            return 0;
        });

        moviesContainer.innerHTML = "";
        movies.forEach(function(movie) {
            moviesContainer.appendChild(movie);
        });
    }
</script>
</body>
</html>
