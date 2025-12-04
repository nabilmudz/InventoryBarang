package services.user;

import exception.InventoryException;
import models.User;
import services.base.BaseService;

public interface UserService extends BaseService<User> {
    void insert(User user) throws InventoryException;
    User findById(int id) throws InventoryException;
    void update(User user) throws InventoryException;
    void delete(int id) throws InventoryException;
    User findByUsername(String username) throws InventoryException;
    User authenticate(String username, String password) throws InventoryException;
}
