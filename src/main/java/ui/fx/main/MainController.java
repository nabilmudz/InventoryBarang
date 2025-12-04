package ui.fx.main;

import facade.InventoryFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import models.User;
import ui.fx.barang.BarangController;
import ui.fx.dashboard.DashboardController;
import ui.fx.laporan.LaporanController;
import ui.fx.supplier.SupplierController;
import ui.fx.transaksi.TransaksiController;

public class MainController {

    @FXML
    private StackPane contentArea;

    private final InventoryFacade facade = new InventoryFacade();
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @FXML
    public void initialize() {
        // showDashboard();
        showTransaksi();
    }

    @FXML
    private void showDashboard() {
        System.out.println("Supplier belum diimplementasi.");
    }

    @FXML
    private void showBarang() {
        System.out.println("Supplier belum diimplementasi.");
    }

    @FXML
    private void showSupplier() {
        System.out.println("Supplier belum diimplementasi.");
    }

    @FXML
    private void showTransaksi() {
        loadView("/ui/fx/transaksi/TransaksiView.fxml", controller -> {
            if (controller instanceof TransaksiController c) c.setFacade(facade);
        });
    }

    @FXML
    private void showLaporan() {
        System.out.println("Supplier belum diimplementasi.");
    }

    private void loadView(String fxmlPath, ControllerConsumer consumer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            Object controller = loader.getController();
            if (consumer != null) consumer.accept(controller);
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface ControllerConsumer {
        void accept(Object controller) throws Exception;
    }
}
