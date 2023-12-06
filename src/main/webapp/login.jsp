<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="styles.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff;
        }
        .form {
            margin: 0;
            display: flex;
            align-items: flex-start;
            justify-content: center;
            height: 100vh;
        }
        .sgbtn {
            margin: 0 auto; /* Center the button horizontally */
            background-color: #3498db; /* Set the background color to blue */
            color: #fff; /* Set the text color to white */
            padding: 8px 16px; /* Adjusted padding for a smaller button */
            font-size: 14px; /* Adjusted font size for a smaller button */
            border: none; /* Remove the default border */
            cursor: pointer;
        }
        header {
            background-color: #fff;
            color: #3498db;
        }
    </style>
</head>
<body>
    <header>
        <h1>Welcome to SkillNest</h1><br>
    </header>
    <div class="form">
        <form id="signupForm" action="LoginServlet" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <input class="sgbtn" type="submit" value="Login">

            <div class="signup-link">
                <p>Don't have an account? <a href="signup.html">Sign Up</a></p>
            </div>
        </form>
    </div>
    
    <footer>
        <p>&copy; 2023 SkillNest. All rights reserved. | <a href="contact.html">Contact Us</a></p>
    </footer>

    <script src="scripts.js"></script>
</body>
</html>
