import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "Saravana$$$14";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Prepare SQL query
            String query = "SELECT * FROM users WHERE username=? AND password=? AND email=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3,email);
//            preparedStatement.setString(4,phone);
            
            // Execute the query
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Successful login
                String retrievedUsername = resultSet.getString("username");
                String retrievedEmail = resultSet.getString("email");
                String retrievedPhone = resultSet.getString("phone");
                String retrievedCourses = resultSet.getString("courses");
                String retrievedSkills = resultSet.getString("skills");
//                String retrievedPhone = resultSet.getString("phone");
                // Create or retrieve the session
                jakarta.servlet.http.HttpSession session = request.getSession();
                
                // Set username as a session attribute
                session.setAttribute("username", retrievedUsername);
                session.setAttribute("email", retrievedEmail);
                session.setAttribute("phone", retrievedPhone);
                session.setAttribute("courses", retrievedCourses);
                session.setAttribute("skills", retrievedSkills);
//                session.setAttribute("phone",retrievedPhone);
                // Forward to user.jsp
                request.getRequestDispatcher("user.jsp").forward(request, response);
            } else {
                // Invalid credentials
                out.println("Invalid username or password. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("An error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Handle exception or log it
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
