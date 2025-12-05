package ui.fx.barang;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Barang;
import facade.InventoryFacade;
import interfaces.Observer;

import java.time.LocalDateTime;

public class BarangController implements Observer {

    @FXML
    private TableView<Barang> tableBarang;

    @FXML
    private TableColumn<Barang, Integer> colId;

    @FXML
    private TableColumn<Barang, String> colNama;

    @FXML
    private TableColumn<Barang, Integer> colStok;

    @FXML
    private TableColumn<Barang, Double> colHarga;

    @FXML
    private TableColumn<Barang, Integer> colSupplier;

    @FXML
    private TextField inputNama;

    @FXML
    private TextField inputStok;

    @FXML
    private TextField inputHarga;

    @FXML
    private TextField inputSupplier;

    @FXML
    private Label statusLabel;

    private InventoryFacade facade;
    private Integer selectedId = null;

    public void setFacade(InventoryFacade facade) {
        this.facade = facade;
        this.facade.registerTransaksiObserver(this);
        loadTable();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        tableBarang.setOnMouseClicked(this::pilihBarang);
    }

    private void loadTable() {
        if (facade == null) {
            return;
        }

        try {
            tableBarang.getItems().setAll(facade.getAllBarang());
        } catch (Exception e) {
            showError("Gagal memuat data: " + e.getMessage());
        }
    }

    @FXML
    private void simpanBarang() {
        try {
            if (inputNama.getText().isEmpty() || inputStok.getText().isEmpty() || inputHarga.getText().isEmpty()) {
                showError("Semua field harus diisi");
                return;
            }

            Barang barang = new Barang();
            barang.setNama(inputNama.getText());
            barang.setStok(Integer.parseInt(inputStok.getText()));
            barang.setHarga(Double.parseDouble(inputHarga.getText()));
            barang.setSupplierId(Integer.parseInt(inputSupplier.getText().isEmpty() ? "0" : inputSupplier.getText()));
            barang.setCreatedAt(LocalDateTime.now());
            barang.setUpdatedAt(LocalDateTime.now());

            facade.addBarang(barang);

            statusLabel.setText("Barang berhasil disimpan");
            statusLabel.setStyle("-fx-text-fill: green;");
            
            clearForm();

        } catch (Exception e) {
            showError("Error menyimpan barang: " + e.getMessage());
        }
    }

    @FXML
    private void pilihBarang(MouseEvent event) {
        Barang barang = tableBarang.getSelectionModel().getSelectedItem();
        if (barang != null) {
            selectedId = barang.getId();
            inputNama.setText(barang.getNama());
            inputStok.setText(String.valueOf(barang.getStok()));
            inputHarga.setText(String.valueOf(barang.getHarga()));
            inputSupplier.setText(String.valueOf(barang.getSupplierId()));
        }
    }

    @FXML
    private void updateBarang() {
        if (selectedId == null) {
            showError("Pilih barang dulu dari tabel!");
            return;
        }

        try {
            Barang barang = new Barang();
            barang.setId(selectedId);
            barang.setNama(inputNama.getText());
            barang.setStok(Integer.parseInt(inputStok.getText()));
            barang.setHarga(Double.parseDouble(inputHarga.getText()));
            barang.setSupplierId(Integer.parseInt(inputSupplier.getText().isEmpty() ? "0" : inputSupplier.getText()));
            barang.setUpdatedAt(LocalDateTime.now());

            facade.updateBarang(barang);

            statusLabel.setText("Barang berhasil diperbarui");
            statusLabel.setStyle("-fx-text-fill: green;");
            
            clearForm();

        } catch (Exception e) {
            showError("Error update barang: " + e.getMessage());
        }
    }

    @FXML
    private void hapusBarang() {
        if (selectedId == null) {
            showError("Pilih barang dulu dari tabel!");
            return;
        }

        try {
            facade.deleteBarang(selectedId);
            
            statusLabel.setText("Barang berhasil dihapus");
            statusLabel.setStyle("-fx-text-fill: green;");
            
            clearForm();
        } catch (Exception e) {
            showError("Error menghapus barang: " + e.getMessage());
        }
    }

    @FXML
    private void clearForm() {
        inputNama.clear();
        inputStok.clear();
        inputHarga.clear();
        inputSupplier.clear();
        selectedId = null;
        statusLabel.setText("");
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    @Override
    public void update() {
        loadTable();
    }
}