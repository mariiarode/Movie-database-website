<%@ page import="model.MVmovie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Movie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .movie-form {
            max-width: 500px;
            margin: 0 auto;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<%
    MVmovie movie = (MVmovie) request.getAttribute("movie");
%>
<div class="container">
    <h1>Edit Movie</h1>
    <form action="MV" method="post" class="movie-form">
        <input type="hidden" name="action" value="updateMovie">
        <input type="hidden" name="id" value="<%= movie.getId() %>">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="<%= movie.getTitle() %>">
        </div>
        <div class="form-group">
            <label for="summary">Synopsis:</label>
            <textarea id="summary" name="summary"><%= movie.getSummary() %></textarea>
        </div>
        <div class="form-group">
            <label for="actors">Actors:</label>
            <input type="text" id="actors" name="actors" value="<%= movie.getActors() %>">
        </div>
        <div class="form-group">
            <label for="producer">Producer:</label>
            <input type="text" id="producer" name="producer" value="<%= movie.getProducer() %>">
        </div>
        <div class="form-group">
            <label for="releaseYear">Release Year:</label>
            <input type="text" id="releaseYear" name="releaseYear" value="<%= movie.getReleaseYear() %>">
        </div>
        <div class="form-group">
            <label for="genre">Genre:</label>
            <input type="text" id="genre" name="genre" value="<%= movie.getGenre() %>">
        </div>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>