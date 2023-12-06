import jakarta.jms.JMSException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "Saravana$$$14";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, JMSException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter();
             Connection connection = (Connection) DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            // Retrieve form parameters
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Prepare SQL query
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect("profile.html");
                } else {
                    out.println("Failed to register user.");
                }
            }
        } catch (SQLException e) {
            // Log the exception and provide a meaningful error message to the client
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | JMSException ex) {
            Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | JMSException ex) {
            Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
