package org.example.webtechproject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
@WebServlet("/databaseServlet")
public class DB extends HttpServlet {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/twitter";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query
            String sql = "SELECT * FROM product";

            // Prepare the statement
            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, request.getParameter("username"));

            // Execute the query
            rs = stmt.executeQuery();

            // Convert ResultSet to JSON format and write to response

            writeResultSetAsJson(out, rs);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Return an error JSON response if something goes wrong
            out.println("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            // Clean up resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to write ResultSet to PrintWriter in JSON format
    private void writeResultSetAsJson(PrintWriter out, ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        out.println("{");
        out.println("\"result\": [");

        while (rs.next()) {
            out.println("{");
            for (int i = 1; i <= columnCount; i++) {
                out.print("\"" + metaData.getColumnLabel(i) + "\": \"" + rs.getString(i) + "\"");
                if (i < columnCount) {
                    out.println(",");
                }
            }
            out.println("}");
            if (!rs.isLast()) {
                out.println(",");
            }
        }

        out.println("]");
        out.println("}");
    }
}