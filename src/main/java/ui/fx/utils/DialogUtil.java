package ui.fx.utils;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class DialogUtil {
    private DialogUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static void tuneDialog(Alert alert) {
        // Biar dialog nggak melebar aneh
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // Kecilkan icon bawaan (info / error)
        Node graphic = alert.getDialogPane().getGraphic();
        if (graphic != null) {
            graphic.setScaleX(0.6);
            graphic.setScaleY(0.6);
        }
    }

    public static void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText("Berhasil");
        alert.setContentText(message);

        tuneDialog(alert);
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Gagal");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(message);

        tuneDialog(alert);
        alert.showAndWait();
    }
}
