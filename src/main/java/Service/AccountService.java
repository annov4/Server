package Service;

import java.sql.*;

public class AccountService {
    private static final String url = "jdbc:h2://Users/anastasianovikova/Desktop/java_junior/Server/db/users";

    private Connection connection;

    public void CreateTable() {
        try {
            // устанавливаем соединение с базой данных
            connection = DriverManager.getConnection(url);

            // создаем таблицу
            Statement createTableStatement = connection.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (login VARCHAR(50), password VARCHAR(50))";
            createTableStatement.executeUpdate(createTableQuery);
            createTableStatement.close();//закрываю соединение
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void insertUser(User user) {
        try {
            connection = DriverManager.getConnection(url);
            String preparedStatementQuery = "INSERT INTO users (login, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementQuery);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByLogin(String login) {
        User user = null;
        try {
            connection = DriverManager.getConnection(url);
            String selectQuery = "SELECT * FROM users WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                user = new User(login, password);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}