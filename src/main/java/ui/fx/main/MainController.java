package ui.fx.main;

import facade.InventoryFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import ui.fx.supplier.SupplierController;
import ui.fx.transaksi.TransaksiController;

public class MainController {

    @FXML
    private StackPane contentArea;

    private final InventoryFacade facade = new InventoryFacade();

    @FXML
    public void initialize() {
        showTransaksi();
    }

    // @FXML
    // private void showDashboard() {
    //     loadView("/ui/fx/dashboard/DashboardView.fxml", null);
    // }

    @FXML
    private void showBarang() {
        loadView("/ui/fx/barang/BarangView.fxml", null);
    }

    @FXML
    private void showSupplier() {
        loadView("/ui/fx/supplier/SupplierView.fxml", controller -> {
            if (controller instanceof SupplierController c) {
                c.setFacade(facade);
            }
        });
    }

    @FXML
    private void showTransaksi() {
        loadView("/ui/fx/transaksi/TransaksiView.fxml", controller -> {
            if (controller instanceof TransaksiController c) {
                c.setFacade(facade);
            }
        });
    }

    @FXML
    private void showLaporan() {
        loadView("/ui/fx/laporan/LaporanView.fxml", null);
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
