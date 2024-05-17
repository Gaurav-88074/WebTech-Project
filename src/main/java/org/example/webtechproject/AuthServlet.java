package org.example.webtechproject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

@WebServlet(name = "AuthServlet", value = "/auth/*")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        switch (pathInfo) {
            case "/login":
                login(request, response);
                break;
            case "/signup":
                signup(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Get JSON data from request
        JSONObject requestObj = getJsonData(request, response);
        JSONObject res = new JSONObject();

        // Get email and password from JSON request
        String email = requestObj.getString("email");
        String password = requestObj.getString("password");

        // Validate email and password (replace with your SQL validation logic)
        if (isValidUser(email, password)) {
            // Generate JWT tokens
            JwtToken jwtTokenObj = new JwtToken();
            Integer subject = getUserId(email,password); // Using email as the subject
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", email);
            // Add additional user info if needed
             userInfo.put("username", getUserName(email,password));

            String accessToken = jwtTokenObj.generateAccessToken(subject, userInfo);
            String refreshToken = jwtTokenObj.generateRefreshToken(subject, userInfo);

            // Prepare response
            res.put("status", "success");
            res.put("refreshToken", refreshToken);
            res.put("accessToken", accessToken);
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
        } else {
            // Invalid credentials
            res.put("status", "error");
            res.put("message", "Invalid email or password");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        }

        // Write response
        PrintWriter out = response.getWriter();
        out.println(res.toString());
    }
    private JSONObject getJsonData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            requestBody.append(line);
        }

        // Parse JSON data
        return new JSONObject(requestBody.toString());
    }
    private void signup(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        JSONObject res = new JSONObject();

        JSONObject obj = getJsonData(request, response);
//        System.out.println(obj);
        // Extract the 'name', 'email', and 'password' fields
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "root");

            // Prepare SQL statement for INSERT
            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);


            // Set parameters
            pstmt.setString(1, obj.getString("name"));
            pstmt.setString(2, obj.getString("email"));
            pstmt.setString(3,  obj.getString("password"));
//            System.out.println(pstmt.toString());
            // Execute the SQL statement
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                res.put("message", "User registered successfully");
                response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                out.println(res.toString());
            } else {
                res.put("message", "User registration failed");
                out.println(res.toString());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            }

            // Clean-up environment
            pstmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            res.put("error", "SQL exception");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.println(res.toString());

        } catch (Exception e) {
            // Handle other exceptions
            res.put("error", "exception");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.println(res.toString());
            e.printStackTrace();
        } finally {
            // Finally block, used to close resources
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException se2) {
            } // Nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // End finally try
        } // End try
    }
    private int getUserId(String email, String password){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int res = -1;
        try {
            // Load JDBC driver (optional for some drivers, required for others)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "root");

            // Create SQL query
            String sql = "SELECT id FROM users WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Execute query
            rs = pstmt.executeQuery();

            // Check if user exists (count > 0)
            if (rs.next()) {
                res  = rs.getInt("id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }private String getUserName(String email, String password){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String res = "no-name";
        try {
            // Load JDBC driver (optional for some drivers, required for others)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "root");

            // Create SQL query
            String sql = "SELECT name FROM users WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Execute query
            rs = pstmt.executeQuery();

            // Check if user exists (count > 0)
            if (rs.next()) {
                res  = rs.getString("name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }
    private boolean isValidUser(String email, String password) {
        // Initialize connection variables
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isValid = false;

        try {
            // Load JDBC driver (optional for some drivers, required for others)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "root");

            // Create SQL query
            String sql = "SELECT COUNT(*) AS count FROM users WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Execute query
            rs = pstmt.executeQuery();

            // Check if user exists (count > 0)
            if (rs.next() && rs.getInt("count") > 0) {
                isValid = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isValid;
    }
}