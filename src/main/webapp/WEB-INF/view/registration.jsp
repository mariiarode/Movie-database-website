<%@ page import="model.MVuser" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - MDB</title>
    <style>
        .hero-container {
            background-color: #f4f4f4;
            text-align: center;
        }
        .registration-form {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 50%;
            padding: 20px;
            border: 2px solid #000; /* Set input border color to black */
            border-radius: 10px;
            background-color: white;
        }

        .registration-form h2 {
            margin-bottom: 10px;
        }

        .registration-form input {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #000; /* Set input border color to black */
            border-radius: 5px;
        }

        .registration-form button {
            width: 50%; /* Adjust button width */
            background-color: #00d4ff;
            color: white;
            border: none;
            padding: 10px;
            margin: 10px 0; /* Increase top and bottom margin */
            cursor: pointer;
            border-radius: 5px;
            align-self: center; /* Center the button horizontally */
        }

        .registration-form button:hover {
            background-color: #00aacc;
        }
    </style>
</head>
<body>
<div class="hero-container">
<div class="registration-form">
    <h2>Register</h2>
    <form action="MV" method="post">
        <input type="hidden" name="action" value="createUser">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Register</button>
    </form>
</div>
</div>
</body>
</html>
