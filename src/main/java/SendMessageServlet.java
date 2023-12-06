import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

public class SendMessageServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "Saravana$$$14";
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SAXException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter();
             Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            // Retrieve form parameters
            String username = request.getParameter("username");
            String message = request.getParameter("message");

            // Prepare SQL query
            String query = "INSERT INTO conversations (username, text) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, message);
                
                
                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Update conversations.xml
                    
                    updateXmlFile(username, message);
                    // Redirect to the community page
                    response.sendRedirect("community.jsp");
                } else {
                    out.println("Failed to register user.");
                }
            }
        } catch (SQLException e) {
            // Log the exception and provide a meaningful error message to the client
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    // Update conversations.xml file with new message
    private void updateXmlFile(String username, String message) {
    try {
        // Load the XML document
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // Assuming the 'conversations.xml' file is in the 'web pages' folder
        String relativePath = getServletContext().getRealPath("/conversations.xml");
        Document doc = docBuilder.parse(relativePath);

        // Get the root element
        Element rootElement = doc.getDocumentElement();

        // Create a new 'conversation' element
        Element conversationElement = doc.createElement("conversation");

        // Create 'username' and 'text' elements and set their values
        Element usernameElement = doc.createElement("username");
        usernameElement.appendChild(doc.createTextNode(username));

        Element textElement = doc.createElement("text");
        textElement.appendChild(doc.createTextNode(message));

        // Append 'username' and 'text' elements to the 'conversation' element
        conversationElement.appendChild(usernameElement);
        conversationElement.appendChild(textElement);

        // Append the new 'conversation' element to the root element
        rootElement.appendChild(conversationElement);

        // Save the updated document back to the XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        // Assuming the 'conversations.xml' file is in the 'web pages' folder
        String outputPath = getServletContext().getRealPath("/conversations.xml");
        StreamResult result = new StreamResult(new File(outputPath));

        transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SAXException ex) {
            Logger.getLogger(SendMessageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SAXException ex) {
            Logger.getLogger(SendMessageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
