package dao.user;

import models.User;
import dao.CrudDAO;

public interface UserDAO extends CrudDAO<User> {
    User findByUsername(String username) throws Exception;
    User authenticate(String username, String password) throws Exception;
}
