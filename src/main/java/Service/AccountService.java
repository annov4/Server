package Service;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, User> loginToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
    }

    public void addNewUser(User user) {
        loginToProfile.put(user.getLogin(), user);
    }

    public boolean checkUser(String login, String password) {
        User user = loginToProfile.get(login);
        return user != null && user.getLogin().equals(login) && user.getPassword().equals(password);
    }
    public boolean checkUser(String password) {
        User user = loginToProfile.get(password);
        return user != null && user.getPassword().equals(password);
    }
}

