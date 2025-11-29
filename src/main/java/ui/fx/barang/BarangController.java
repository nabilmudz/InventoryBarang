package ui.fx.barang;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import models.Barang;
import services.barang.BarangService;
import services.barang.BarangServiceImpl;

public class BarangController {

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
    private Button btnSimpan;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnHapus;

    private BarangService barangService = new BarangServiceImpl();

    private Integer selectedId = null;


    @FXML
    public void initialize() {
        // binding kolom
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNama.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNama()));
        colStok.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStok()).asObject());
        colHarga.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getHarga()).asObject());
        colSupplier.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getSupplierId()).asObject());

        loadTable();
    }


    // 🔹 Load data ke TableView
    private void loadTable() {
        tableBarang.getItems().setAll(barangService.getAllBarang());
    }


    // 🔹 Simpan barang baru
    @FXML
    private void simpanBarang() {
        try {
            Barang barang = new Barang();
            barang.setNama(inputNama.getText());
            barang.setStok(Integer.parseInt(inputStok.getText()));
            barang.setHarga(Double.parseDouble(inputHarga.getText()));
            barang.setSupplierId(Integer.parseInt(inputSupplier.getText()));

            barangService.addBarang(barang);

            clearForm();
            loadTable();

        } catch (Exception e) {
            showError("Error menyimpan barang: " + e.getMessage());
        }
    }


    // 🔹 Pilih baris tabel → tampilkan di form
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


    // 🔹 Update barang
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
            barang.setSupplierId(Integer.parseInt(inputSupplier.getText()));

            barangService.updateBarang(barang);

            clearForm();
            loadTable();

        } catch (Exception e) {
            showError("Error update barang: " + e.getMessage());
        }
    }


    // 🔹 Delete barang
    @FXML
    private void hapusBarang() {
        if (selectedId == null) {
            showError("Pilih barang dulu dari tabel!");
            return;
        }

        try {
            barangService.deleteBarang(selectedId);
            clearForm();
            loadTable();
        } catch (Exception e) {
            showError("Error menghapus barang: " + e.getMessage());
        }
    }


    // 🔹 Clear form
    private void clearForm() {
        inputNama.clear();
        inputStok.clear();
        inputHarga.clear();
        inputSupplier.clear();
        selectedId = null;
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
