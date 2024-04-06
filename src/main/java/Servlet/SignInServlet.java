package Servlet;

import Service.AccountService;
import Service.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Enter login and password");
            return;
        }

        try {
            User user = accountService.getUserByLogin(login);
            if (user == null || !user.getPassword().equals(password)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Incorrect password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            response.getWriter().println("Authorized: " + login);
        try {
            accountService.insertUser(new User(login, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}