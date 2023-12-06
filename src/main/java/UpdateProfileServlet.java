import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "Saravana$$$14";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        // Retrieve skills from the request
        String[] skillsArray = request.getParameter("skills").split(", "); // Assuming skills are comma-separated
        
        jakarta.servlet.http.HttpSession session = request.getSession();
        // Convert the array to a PostgreSQL array representation
        String skillsArrayStr = "{" + String.join(",", skillsArray) + "}";

                // Retrieve the uploaded profile photo
        Part filePart = request.getPart("profilePhoto");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Save the image to a directory on the server
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        File img1 = new File(uploadPath + File.separator + fileName);
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, img1.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        // Save the file path in the session
        session.setAttribute("profilePhotoPath", img1.getAbsolutePath());
        // Update the user's skills in the session
        session.setAttribute("username", username);
        session.setAttribute("phone", phone);
        session.setAttribute("skills", skillsArrayStr);

// Update the user's skills in the database (assuming you have a method to update user details)
// For example:
// userService.updateSkills(username, skillsArray);

        File img = new File("nullimg.png");
        // Retrieve the uploaded profile photo
//        InputStream profilePhotoInputStream = null ;
//        Part file_part = request.getPart("profilePhoto");
//        if(file_part != null ) {
//            profilePhotoInputStream = file_part.getInputStream();
//        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Prepare SQL query to update user profile
            String query = "UPDATE users SET email=?, phone=?, skills=?, profile_photo=? WHERE username=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);
            preparedStatement.setArray(3, connection.createArrayOf("text", skillsArray));

            // Check if the file exists before trying to set it
            if (img.exists()) {
                try (FileInputStream fin = new FileInputStream(img)) {
                    // Convert the input stream to a byte array
                    byte[] photoBytes = fin.readAllBytes();

                    // Set the byte array as a parameter
                    preparedStatement.setBytes(4, photoBytes);
                } catch (IOException ex) {
                    Logger.getLogger(UpdateProfileServlet.class.getName()).log(
                            Level.SEVERE, ex.getMessage(), ex);
                }
            } else {
                // If the file doesn't exist, set the parameter to null
                preparedStatement.setNull(4, java.sql.Types.BINARY);
            }

            preparedStatement.setString(5, username);



            // Execute the query
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                // Profile updated successfully
                response.sendRedirect("user.jsp");
            } else {
                // Profile update failed
                response.getWriter().println("Failed to update user profile.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions (log or send an error response)
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Handle exceptions (log or send an error response)
                response.getWriter().println("An error occurred: " + e.getMessage());
            }
        }
    }
}
