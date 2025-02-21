package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/register")
public class UserRegistrationSystem extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Hash the password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Save user data to the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeedata", "root", "MyDataBase");
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.executeUpdate();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        response.setContentType("text/html");
        response.getWriter().println("Registration successful!");
    }
}
