<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses - SkillNest</title>
    <link rel="stylesheet" href="styles.css">
    
</head>
<body>
    <%
            String username = (String) session.getAttribute("username");
            String email = (String) session.getAttribute("email");
    %>
    <header>
        <h1>SkillNest Courses</h1><br>
        <nav class="banner-navigation">
            <a href="index.html">Home</a>
            <a href="course.jsp">Courses</a>
            <a href="community.jsp">Community</a>
            <div class="user-profile">
                <a href="profile.html" class="user-profile">My Profile</a>
            </div>
        </nav>
    </header>

    <main class="courses-page">
        <section class="course">
            <img src="web_dev.png" alt="Course 1">
            <h2>Web Development Fundamentals</h2>
            <p>Learn the basics of web development, including HTML, CSS, and JavaScript.</p>
            <a href="enrollment.jsp?course=Web_Development&username=<%= session.getAttribute("username") %>" class="btn">Enroll Now</a>
        </section>

        <section class="course">
            <img src="data_sci.png" alt="Course 2">
            <h2>Data Science Essentials</h2>
            <p>Explore the fundamental concepts of data science and analysis.</p>
            <a href="enrollment.jsp?course=Data_Science&username=<%= session.getAttribute("username") %>" class="btn">Enroll Now</a>
        </section>
        
        <section class="course">
            <img src="dp.png" alt="Dynamic Programming">
            <h2>Dynamic Programming</h2>
            <p>Master the art of solving complex problems with dynamic programming techniques.</p>
            <a href="enrollment.jsp?course=Dynamic_Programming&username=<%= session.getAttribute("username") %>" class="btn">Enroll Now</a>
        </section>

        <section class="course">
            <img src="system_design.png" alt="System Design">
            <h2>System Design</h2>
            <p>Learn to design scalable and reliable systems for modern applications.</p>
            <a href="enrollment.jsp?course=System_Design&username=<%= session.getAttribute("username") %>" class="btn">Enroll Now</a>
        </section>

        <section class="course">
            <img src="dsa.png" alt="Data Structures and Algorithms">
            <h2>Data Structures and Algorithms</h2>
            <p>Explore fundamental data structures and algorithms for efficient problem-solving.</p>
            <a href="enrollment.jsp?course=Data_Structures_Algorithms&username=<%= session.getAttribute("username") %>" class="btn">Enroll Now</a>
        </section>
        
        <section class="course">
            <img src="ml.png" alt="Machine Learning">
            <h2>Machine Learning</h2>
            <p>Explore the world of machine learning and artificial intelligence.</p>
            <a href="enrollment.jsp?course=Machine_Learning&username=<%= session.getAttribute("username") %>" class="btn">Enroll Now</a>
        </section>
    </main>

    <footer>
        <p>&copy; 2023 SkillNest. All rights reserved. | <a href="contact.html">Contact Us</a></p>
    </footer>

    <script src="scripts.js"></script>
</body>
</html>
