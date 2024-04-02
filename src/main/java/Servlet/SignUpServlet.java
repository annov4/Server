package Servlet;

import Service.AccountService;
import Service.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        } else if (accountService.checkUser(login, password) == true) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().println("User with this login is already registered");
            return;
        }
        accountService.addNewUser(new User(login, password));
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("User registered");
    }
}