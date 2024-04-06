package Servlet;

import Service.AccountService;
import Service.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
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
            if (user.getLogin().equals(login)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().println("User with this login is already registered");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        User user = new User(login, password);
                try {
                    accountService.insertUser(user);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("User registered");
    }
}