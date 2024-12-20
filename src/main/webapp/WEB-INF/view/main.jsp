<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <style>
        .hero-container {
            background-color: #f4f4f4;
            text-align: center;
        }
        .search-bar {
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
            width: 80%;
            max-width: 800px;
            margin: 0 auto;
        }

        .search-bar input {
            width: calc(100% - 80px); /* Adjusted to accommodate the button width */
            padding: 15px;
            font-size: 16px;
            border: none;
            border-radius: 30px 0 0 30px;
            outline: none;
        }

        .search-bar button {
            padding: 15px 20px;
            font-size: 16px;
            border: none;
            border-radius: 0 30px 30px 0;
            background-color: #00d4ff;
            color: white;
            cursor: pointer;
        }

        .search-bar button:hover {
            background-color: #00aacc;
        }

        #suggestions {
            width: calc(100% - 80px); /* Same width as input field */
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            position: absolute;
            z-index: 1;
            top: calc(100% + 10px); /* Positioned below the search bar */
            left: 0;
            display: none;
        }
        #suggestionsList {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        #suggestionsList li {
            padding: 10px;
            cursor: pointer;
            color: black; /* Text color set to black */
        }
        #suggestionsList li:hover {
            background-color: #f4f4f4;
        }
    </style>

    <script>
        function searchMovies() {
            var searchInput = document.getElementById("searchInput").value;
            window.location.href = "MV?searchTitle=" + searchInput;
        }

        function fetchSuggestions() {
            var input = document.getElementById("searchInput").value.toLowerCase();
            if (input.length === 0) {
                document.getElementById("suggestions").style.display = "none";
                return;
            }

            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState === 4 && this.status === 200) {
                    var response = JSON.parse(this.responseText);
                    var suggestions = response.suggestions;
                    var suggestionsList = document.getElementById("suggestionsList");
                    suggestionsList.innerHTML = "";
                    suggestions.forEach(function(title) {
                        var listItem = document.createElement("li");
                        listItem.textContent = title;
                        listItem.onclick = function() {
                            document.getElementById("searchInput").value = title;
                            document.getElementById("suggestions").style.display = "none";
                        };
                        suggestionsList.appendChild(listItem);
                    });
                    document.getElementById("suggestions").style.display = "block";
                }
            };
            xhttp.open("POST", "suggestionsFrontend", true);
            xhttp.setRequestHeader("Content-Type", "application/json");
            xhttp.send(JSON.stringify({ value: input }));
        }
    </script>
</head>
<body>
<div class="hero-container">
    <div class="hero-section">
        <div class="hero-text">
            <h1>Welcome to MDB</h1>
        </div>
        <div class="search-bar">
            <input type="text" id="searchInput" placeholder="Search movies..." oninput="fetchSuggestions()">
            <button onclick="searchMovies()">Search</button>
            <div id="suggestions">
                <ul id="suggestionsList"></ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
