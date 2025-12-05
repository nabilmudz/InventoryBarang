package services.user;

import dao.user.UserDAO;
import dao.user.UserDAOImpl;
import exception.InventoryException;
import models.User;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public void create(User user) throws InventoryException {
        try {
            if (user.getUsername() == null || user.getUsername().isBlank()) {
                throw new InventoryException("Username tidak boleh kosong");
            }
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                throw new InventoryException("Password tidak boleh kosong");
            }
            if (user.getRole() == null || user.getRole().isBlank()) {
                throw new InventoryException("Role tidak boleh kosong");
            }
            userDAO.insert(user);
        } catch (InventoryException e) {
            throw e;
        } catch (Exception e) {
            throw new InventoryException("Gagal membuat user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws InventoryException {
        try {
            return userDAO.findAll();
        } catch (Exception e) {
            throw new InventoryException("Gagal membaca semua user: " + e.getMessage());
        }
    }

    @Override
    public void insert(User user) throws InventoryException {
        create(user);
    }

    @Override
    public User findById(int id) throws InventoryException {
        try {
            return userDAO.findById(id);
        } catch (Exception e) {
            throw new InventoryException("Gagal membaca user: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) throws InventoryException {
        try {
            userDAO.update(user);
        } catch (Exception e) {
            throw new InventoryException("Gagal mengupdate user: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws InventoryException {
        try {
            userDAO.delete(id);
        } catch (Exception e) {
            throw new InventoryException("Gagal menghapus user: " + e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username) throws InventoryException {
        try {
            User user = userDAO.findByUsername(username);
            if (user == null) {
                throw new InventoryException("User tidak ditemukan");
            }
            return user;
        } catch (InventoryException e) {
            throw e;
        } catch (Exception e) {
            throw new InventoryException("Gagal mencari user: " + e.getMessage());
        }
    }

    @Override
    public User authenticate(String username, String password) throws InventoryException {
        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                throw new InventoryException("Username atau password salah");
            }
            return user;
        } catch (InventoryException e) {
            throw e;
        } catch (Exception e) {
            throw new InventoryException("Gagal autentikasi: " + e.getMessage());
        }
    }
}
