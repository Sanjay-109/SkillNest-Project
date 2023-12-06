/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class EnrollmentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String[] selectedCourses = request.getParameterValues("course");

        // JDBC Database Connection Parameters
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String jdbcUsername = "postgres";
        String jdbcPassword = "Saravana$$$14";

        try {
            // Load the JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
                // Prepare and execute the SQL statement
                String sql = "UPDATE users SET phone=?, courses=? WHERE username=? AND email=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, phone);
                    preparedStatement.setArray(2, connection.createArrayOf("text", selectedCourses));
                    preparedStatement.setString(3, username);
                    preparedStatement.setString(4,email);

                    // Convert the array to a PostgreSQL ARRAY string
                    

             

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        // Successful enrollment, redirect to course.jsp
                        response.sendRedirect("course.jsp");
                    } else {
                        // Failed to enroll, handle accordingly
                        response.getWriter().println("Error: Failed to enroll. Please try again.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: An unexpected error occurred. Please try again later.");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
