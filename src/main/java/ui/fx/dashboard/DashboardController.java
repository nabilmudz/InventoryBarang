package ui.fx.dashboard;

import facade.InventoryFacade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Transaksi;

import java.util.List;

public class DashboardController {

    @FXML
    private Label lblTotalBarang;

    @FXML
    private Label lblTotalSupplier;

    @FXML
    private Label lblTotalTransaksi;

    @FXML
    private Label lblLowStock;

    @FXML
    private TableView<Transaksi> tableTransaksi;

    @FXML
    private TableColumn<Transaksi, Integer> colId;

    @FXML
    private TableColumn<Transaksi, Integer> colBarangId;

    @FXML
    private TableColumn<Transaksi, Integer> colQty;

    @FXML
    private TableColumn<Transaksi, String> colJenis;

    @FXML
    private TableColumn<Transaksi, String> colTanggal;

    private InventoryFacade facade;

    public void setFacade(InventoryFacade facade) {
        this.facade = facade;
        loadDashboard();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBarangId.setCellValueFactory(new PropertyValueFactory<>("barangId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
    }

    private void loadDashboard() {
        try {
            if (facade == null) return;

            // Update summary cards
            int totalBarang = facade.getAllBarang().size();
            int totalSupplier = facade.listSupplier().size();
            int totalTransaksi = facade.listTransaksi().size();
            int lowStock = (int) facade.getAllBarang().stream()
                    .filter(b -> b.getStok() < 10)
                    .count();

            lblTotalBarang.setText(String.valueOf(totalBarang));
            lblTotalSupplier.setText(String.valueOf(totalSupplier));
            lblTotalTransaksi.setText(String.valueOf(totalTransaksi));
            lblLowStock.setText(String.valueOf(lowStock));

            // Load recent transactions
            List<Transaksi> transaksiList = facade.listTransaksi();
            if (transaksiList.size() > 10) {
                tableTransaksi.getItems().setAll(transaksiList.subList(0, 10));
            } else {
                tableTransaksi.getItems().setAll(transaksiList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}