package main;

import Service.AccountService;
import Servlet.SignInServlet;
import Servlet.SignUpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");//все запросы начинающиеся с signin будут направлены в сервлет SignInServlet
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");


        server.setHandler(context);

        server.start();
        System.out.println("Server started!");
        server.join();
    }
}