import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class FormDataSubmission {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hello";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    // SQL query to insert data into the database
    private static final String INSERT_QUERY = "INSERT INTO user_data (name, email, age, dob) VALUES (?, ?, ?, ?)";
    // SQL query to retrieve data from the database
    private static final String RETRIEVE_QUERY = "SELECT * FROM user_data";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/submitFormData", new FormDataHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000...");
    }

    static class FormDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    // Parse form data from the request body
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    String[] formData = requestBody.split("&");
                    String name = formData[0].split("=")[1];
                    String email = formData[1].split("=")[1];
                    int age = Integer.parseInt(formData[2].split("=")[1]);
                    String dob = formData[3].split("=")[1];

                    // Insert form data into the database
                    boolean inserted = insertUserData(name, email, age, dob);

                    // Retrieve all data from the database
                    String tableContent = retrieveDataFromDatabase();

                    // Prepare HTML response with the data
                    String htmlResponse = "<html><head><title>Submitted Data</title></head><body>";
                    htmlResponse += "<h1 style='color:green'>Submitted Successfully.....</h1>";
                    htmlResponse += "<table border='1'><tr><th>Name</th><th>Email</th><th>Age</th><th>Date of Birth</th></tr>";
                    htmlResponse += tableContent;
                    htmlResponse += "</table></body></html>";

                    // Send HTML response
                    exchange.sendResponseHeaders(200, htmlResponse.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(htmlResponse.getBytes());
                    os.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, 0); // Internal Server Error
                    exchange.getResponseBody().close();
                }
            } else {
                exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                exchange.getResponseBody().close();
            }
        }

        private boolean insertUserData(String name, String email, int age, String dob) throws SQLException {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, dob);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        }

        private String retrieveDataFromDatabase() throws SQLException {
            StringBuilder tableContent = new StringBuilder();
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    int age = resultSet.getInt("age");
                    String dob = resultSet.getString("dob");
                    tableContent.append("<tr><td>").append(name).append("</td><td>").append(email).append("</td><td>")
                            .append(age).append("</td><td>").append(dob).append("</td></tr>");
                }
            }
            return tableContent.toString();
        }
    }
}
