package ui.fx.login;

import exception.InventoryException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.user.UserService;
import services.user.UserServiceImpl;
import ui.fx.main.MainController;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final UserService userService = new UserServiceImpl();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username dan password tidak boleh kosong");
            return;
        }

        try {
            User user = userService.authenticate(username, password);
            if (user != null) {
                openMainApp(user);
            }
        } catch (InventoryException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        passwordField.clear();
    }

    private void openMainApp(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/fx/main/MainLayout.fxml")
            );
            Scene scene = new Scene(loader.load(), 1024, 600);

            MainController controller = loader.getController();
            controller.setCurrentUser(user);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Inventaris Barang - " + user.getUsername());
        } catch (Exception e) {
            showError("Gagal membuka aplikasi: " + e.getMessage());
        }
    }
}
