<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SkillNest - Community</title>
    <link rel="stylesheet" href="styles.css">
    <style>
    .community {
        max-width: 800px;
        margin: 20px auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    p {
        margin-bottom: 20px;
    }

    button {
        background-color: #333;
        color: #fff;
        padding: 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        margin-bottom: 20px;
    }

    #conversationsContainer {
        margin-top: 20px;
    }

    .discussion-thread {
        border: 1px solid #ddd;
        padding: 15px;
        margin-bottom: 15px;
        border-radius: 8px;
        background-color: #f9f9f9;
    }

    h3 {
        color: #333;
        margin-bottom: 10px;
    }

    p {
        color: #555;
        margin-bottom: 0;
    }

    form {
        margin-top: 20px;
        display: flex; /* Display children elements in a row */
        flex-wrap: wrap; /* Allow items to wrap onto multiple lines */
    }

    textarea {
        flex: 1; /* Take remaining space in the row */
        padding: 15px;
        margin-bottom: 15px;
        box-sizing: border-box;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 16px;
        resize: vertical; /* Allow vertical resizing */
        height: 150px; /* Adjust the height as needed */
    }
    input[type="submit"] {
        background-color: #333;
        color: #fff;
        padding: 12px 20px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        font-size: 16px;
        margin-left: 10px; /* Add some space between textarea and button */
    }

    input[type="submit"]:hover {
        background-color: #555;
    }
    </style>



</head>
<body>
    <header>
        <h1>SkillNest Community Forum</h1><br>
        <nav class="banner-navigation">
            <a href="index.html">Home</a>
            <a href="course.jsp">Courses</a>
            <a href="community.jsp">Community</a>
            <div class="user-profile">
                <a href="profile.html" class="user-profile">My Profile</a>
            </div>
        </nav>
    </header>

   <section class="community">
        <p>Welcome to the SkillNest Community Forum. Join discussions, ask questions, and share with fellow learners!</p>

        <!-- Sample Discussion Threads -->
        <button id="fetchConversationsBtn" onclick="fetchAndDisplayConversations()">Fetch Conversations</button>

        <!-- Add more discussion threads as needed -->
        <div id="conversationsContainer"></div>

        <!-- Form for sending messages with submit button and action -->
        <%
            String username = (String) session.getAttribute("username");
        %>
        <form id="messageForm" action="SendMessageServlet" method="POST">
            <textarea id="message" name="message" placeholder="Type your message"></textarea>
            <input type="hidden" id="usernameHidden" name="username" value="<%= username %>">
            <input type="submit" value="Send Message">
        </form>
    </section>
   
    <footer>
        <p>&copy; 2023 SkillNest. All rights reserved. | <a href="contact.html">Contact Us</a></p>
    </footer>

    <script>
        // Function to fetch and display conversations from an XML file
        function fetchAndDisplayConversations() {
            // Replace with the correct path to your XML file
            const xmlUrl = 'conversations.xml';

            // Assuming you are using fetch API to get data
            fetch(xmlUrl)
                .then(response => response.text())
                .then(xmlText => {
                    // Parse the XML text into a DOM document
                    const parser = new DOMParser();
                    const xmlDoc = parser.parseFromString(xmlText, 'application/xml');

                    // Handle the parsed XML document and display it in the conversationsContainer
                    const conversationsContainer = document.getElementById('conversationsContainer');

                    // Clear previous content
                    conversationsContainer.innerHTML = '';

                    // Iterate through the XML nodes and create HTML elements for each conversation
                    const conversationNodes = xmlDoc.querySelectorAll('conversation');
                    conversationNodes.forEach(conversationNode => {
                        const conversationThread = document.createElement('div');
                        conversationThread.classList.add('discussion-thread');

                        const title = document.createElement('h3');
                        title.textContent = conversationNode.querySelector('username').textContent;

                        const content = document.createElement('p');
                        content.textContent = conversationNode.querySelector('text').textContent;

                        conversationThread.appendChild(title);
                        conversationThread.appendChild(content);

                        conversationsContainer.appendChild(conversationThread);
                    });
                })
                .catch(error => console.error('Error fetching conversations:', error));
        }
    </script>
</body>
</html>
