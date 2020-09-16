package service;

import java.util.ArrayList;
import java.util.List;


public class UserService {

    private List<String> users = new ArrayList();

    public UserService() {
        users.add("Ivan");
        users.add("Pablo");
    }

    public List findAll() {
        return users;
    }

    public String findByUsername(String username) {
        return users.stream()
                .filter(b -> b.equals(username))
                .findFirst()
                .get();
    }

    public void create(String user) {
        users.add(user);
    }
}
