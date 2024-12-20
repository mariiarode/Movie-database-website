<%@ page import="java.util.List" %>
<%@ page import="model.MVuser" %>
<%@ page import="database.USERdb" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - MDB</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .admin-users {
            margin: 50px auto;
            max-width: 800px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .admin-users h2 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        .user-action-form {
            display: inline-block;
            margin-right: 10px;
        }

        .user-action-form select, .user-action-form button {
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .user-action-form select {
            margin-right: 10px;
        }

        .user-action-form button.change-button {
            background-color: #007bff;
            color: #fff;
        }

        .user-action-form button.change-button:hover {
            background-color: #0056b3;
        }

        .user-action-form button.delete-button {
            background-color: #dc3545;
            color: #fff;
        }

        .user-action-form button.delete-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

<div class="admin-users">
    <h2>User Management</h2>
    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Privileges</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            USERdb userdb = new USERdb();
            List<MVuser> users = userdb.getAllUsers();
            userdb.closeConnection();

            for (MVuser user : users) {
        %>
        <tr>
            <td><%= user.getLogin() %></td>
            <td><%= user.getPrivileges() == 2 ? "Admin" : "User" %></td>
            <td>
                <form action="MV" method="post" class="user-action-form">
                    <input type="hidden" name="action" value="changePrivileges">
                    <input type="hidden" name="userId" value="<%= user.getId() %>">
                    <select name="newPrivileges">
                        <option value="1" <%= user.getPrivileges() == 1 ? "selected" : "" %>>User</option>
                        <option value="2" <%= user.getPrivileges() == 2 ? "selected" : "" %>>Admin</option>
                    </select>
                    <button type="submit" class="change-button">Change</button>
                </form>
                <form action="MV" method="post" class="user-action-form">
                    <input type="hidden" name="action" value="deleteUser">
                    <input type="hidden" name="userId" value="<%= user.getId() %>">
                    <button type="submit" class="delete-button">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
