package sexy.criss.task.core.jdbc.dao;

import sexy.criss.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void createUsersTable();
    void dropUsersTable();
    void saveUser(String name, String lastName, byte age);
    void removeUserById(long id);
    void cleanUsersTable();
}