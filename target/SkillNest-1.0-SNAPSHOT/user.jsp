<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Page</title>
    <!-- Include FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    <!-- Your existing styles -->
    <link rel="stylesheet" href="styles.css">    <style>
        #container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-between;
            min-height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #fff;
        }

        header {
            text-align: center;
        }

        #profileImage {
            display: none;
            width: 100px;
            height: 100px;
            border-radius: 50%;
            margin-top: 20px;
        }

        #userProfileForm {
            display: flex;
            flex-direction: column;
            align-items: center;
            max-width: 400px;
            width: 100%;
            margin: 20px auto; /* Adjust the margin for the form container */
            padding: 20px;
            box-sizing: border-box;
            margin-top: 20px;
            margin-bottom: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label, input {
            margin-top: 10px;
            text-align: left;
        }

        button {
            margin-top: 15px;
        }
        .custom-file-upload {
            display: inline-block;
            position: relative;
        }

        .file-label {
            display: inline-block;
            background-color: #3498db;
            color: #fff;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .file-label:hover {
            background-color: #2980b9;
        }

        .file-input {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }

        /* Optional: Add an icon for a better visual indication */
        .fa-cloud-upload {
            margin-right: 5px;
        }
        #addedSkills {
            margin-top: 10px;
        }

        .added-skill {
            background-color: #4CAF50;
            color: white;
            padding: 5px;
            margin-right: 5px;
            border-radius: 3px;
            display: inline-block;
        }

        footer {
            text-align: center;
            padding: 10px;
            width: 100%;
        }
        .custom-file-upload {
            display: inline-block;
            position: relative;
        }

        .file-label {
            display: inline-block;
            background-color: #3498db;
            color: #fff;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .file-label:hover {
            background-color: #2980b9;
        }

        .file-input {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }

        .fa-cloud-upload {
            margin-right: 5px;
        }

        
    </style>
</head>
<body>
    
    <header>
        <h1>Welcome, <%= session.getAttribute("username") %>! to SkillNest</h1><br>
         <nav class="banner-navigation">
            <a href="index.html">Home</a>
            <a href="course.jsp?username=<%= session.getAttribute("username") %>&email=<%= session.getAttribute("email") %>">Courses</a>
            <a href="community.jsp?username=<%= session.getAttribute("username") %>">Community</a>
            <div class="user-profile">
                <a href="profile.html" class="user-profile">My Profile</a>
            </div>
        </nav>
    </header>

    <div id="container">

        <!-- Display uploaded profile photo -->
        <img id="profileImage" src="<%= session.getAttribute("profilePhotoPath")%>" alt="Profile Photo">

        <!-- Form to edit user profile -->
        <form id="userProfileForm" action="UpdateProfileServlet" method="post" enctype="multipart/form-data">
            <!-- Displayed field for username (readonly) -->
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="<%= session.getAttribute("username") %>" readonly>

            <!-- Profile Photo Upload -->
            <div class="custom-file-upload">
                <label for="profilePhoto" class="file-label">       
                    <i class="fa fa-cloud-upload"></i>
                    <span>Choose a profile photo</span>
                </label>
                <input type="file" id="profilePhoto" name="profilePhoto" accept="image/*" class="file-input">
            </div>

            <!-- Email (editable) -->
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= session.getAttribute("email") %>">

            <!-- Phone Number (editable) -->
            <label for="phone">Phone Number:</label>
            <input type="tel" id="phone" name="phone" value="<%= session.getAttribute("phone") %>">

            <!-- Connections (readonly) -->
            <label for="courses">Courses Enrolled:</label>
            <input type="text" id="courses" name="courses" value="<%= session.getAttribute("courses") %>" readonly>

            <!-- Skills -->
            <label for="skills">Skills:</label>
            <input type="text" id="skills" name="skills" value="<%= session.getAttribute("skills") %>">

            <!-- Add Skill dropdown and button -->
            <label for="addSkill">Add Skill:</label>
            <select id="addSkill" name="addSkill">
                <option value="Java">Java</option>
                <option value="JavaScript">JavaScript</option>
                <option value="Python">Python</option>
                <!-- Add more options as needed -->
            </select>
            <button id="addSkill-btn" type="button" onclick="addSkill()">Add Skill</button>

            <!-- Display added skills -->
            <div id="addedSkills"></div>

            <!-- Submit Button -->
            <button type="submit">Update Profile</button>
        </form>

        <footer>
            <p>&copy; 2023 SkillNest. All rights reserved. | <a href="contact.html">Contact Us</a></p>
        </footer>
    </div>

    <script>
        document.getElementById('profilePhoto').addEventListener('change', function () {
            var profileImage = document.getElementById('profileImage');
            var fileInput = this;

            // Ensure a file is selected
            if (fileInput.files && fileInput.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    profileImage.style.display = 'block';
                    profileImage.src = e.target.result;
                };

                reader.readAsDataURL(fileInput.files[0]);
            } else {
                profileImage.style.display = 'none';
            }
        });
        
        document.getElementById('addSkill-btn').addEventListener('click', addSkill);
        function addSkill() {
            var addSkillSelect = document.getElementById('addSkill');
            var selectedSkill = addSkillSelect.options[addSkillSelect.selectedIndex].value;

            var skillsInput = document.getElementById('skills');
            var addedSkillsDiv = document.getElementById('addedSkills');

            // Check if the skill is not already added
            if (skillsInput.value.indexOf(selectedSkill) === -1) {
                // Add the skill to the skills input
                skillsInput.value += (skillsInput.value === '') ? selectedSkill : ', ' + selectedSkill;

                // Display the added skill in the addedSkillsDiv
                var addedSkillElement = document.createElement('span');
                addedSkillElement.textContent = selectedSkill;
                addedSkillElement.className = 'added-skill';
                addedSkillsDiv.appendChild(addedSkillElement);
            }
        }
    </script>
</body>
</html>