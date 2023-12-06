<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enrollment - SkillNest</title>
    <!--    <link rel="stylesheet" href="styles.css">-->
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 1em;
        }

        main {
            max-width: 600px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 60px; /* Adjust the margin to create space for the footer */
        }

        h2 {
            color: #333;
        }

        form {
            display: grid;
            gap: 10px;
        }

        label {
            font-weight: bold;
        }

        input,
        select,
        textarea {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            background-color: #333;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #555;
        }

        #enrollmentStatus {
            margin-top: 10px;
            color: #333;
        }

        footer {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 1em;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>

</head>
<body>
<header>
    <h1>Enrollment</h1><br>
    <!-- You can include a navigation bar if needed -->
</header>

<main>
    <h2>Enroll in Course</h2>

    <form id="enrollmentForm" action="EnrollmentServlet" method="POST">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="<%= session.getAttribute("username") %>" required readonly>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%= session.getAttribute("email") %>" required readonly>

        <label for="phone">Phone:</label>
        <input type="tel" id="phone" name="phone" required>

        <label for="course">Select Course:</label>
        <select id="course" name="course">
            <option value="Web_Development">Web Development Fundamentals</option>
            <option value="Data_Science">Data Science Essentials</option>
            <option value="Dynamic_Programming">Dynamic Programming</option>
            <option value="System_Design">System Design</option>
            <option value="DSA">Data Structures and Algorithms</option>
            <option value="Machine_Learning">Machine Learning</option>
            <!-- Add options for other courses -->
        </select>

        <label for="comments">Comments:</label>
        <textarea id="comments" name="comments" rows="4"></textarea>

        <button type="submit" onclick="submitForm()">Submit</button>
    </form>

    <div id="enrollmentStatus"></div>
</main>

<footer>
    <p>&copy; 2023 SkillNest. All rights reserved. | <a href="contact.html">Contact Us</a></p>
</footer>
<script>
    async function submitForm() {
        const form = document.getElementById('enrollmentForm');
        const statusDiv = document.getElementById('enrollmentStatus');

        // Perform client-side validation (you can add more validation rules)
        if (!form.checkValidity()) {
            statusDiv.innerHTML = 'Please fill out all required fields.';
            return;
        }

        // Prepare data for submission
        const formData = new FormData(form);

        try {
            // Simulate an asynchronous request to the server (replace with actual AJAX code)
            const response = await fetch('your_backend_endpoint', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const data = await response.json();
                // Display enrolled status or any other response from the server
                statusDiv.innerHTML = data.status;
            } else {
                statusDiv.innerHTML = 'Error submitting the form. Please try again.';
            }
        } catch (error) {
            console.error('An error occurred:', error);
            statusDiv.innerHTML = 'An unexpected error occurred. Please try again later.';
        }
    }
</script>
</body>
</html>
