import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ImageUploadServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5 MB max file size
public class ImageUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "Saravana$$$14";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InputStream inputStream = null; // Input stream of the upload file

        // Retrieves <input type="file" name="file">
        Part filePart = request.getPart("file");

        if (filePart != null) {
            // Prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            // Obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        Connection connection = null; // Connection to the database
        String message = null; // Message will be sent back to client

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to insert the image into the database
            String sql = "INSERT INTO images (image_data) values ( ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setString(1, "uploaded_image"); // You can customize the name
            statement.setBlob(2, inputStream);

            // Sends the statement to the database server
            int row = statement.executeUpdate();

            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (ClassNotFoundException | SQLException e) {
            message = "ERROR: " + e.getMessage();
        } finally {
            // Closes the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
            // Closes the input stream of the upload file
            if (inputStream != null) {
                inputStream.close();
            }

            // Sets the message in request scope
            request.setAttribute("Message", message);

            // Forwards to the message page
            getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

}
