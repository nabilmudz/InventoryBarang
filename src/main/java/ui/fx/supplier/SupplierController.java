package ui.fx.supplier;

import facade.InventoryFacade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Supplier;

import java.util.List;

public class SupplierController {

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtKontak;

    @FXML
    private TextField txtAlamat;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TableColumn<Supplier, Integer> colId;

    @FXML
    private TableColumn<Supplier, String> colNama;

    @FXML
    private TableColumn<Supplier, String> colKontak;

    @FXML
    private TableColumn<Supplier, String> colAlamat;

    @FXML
    private Label lblStatus;

    private InventoryFacade facade;

    private Supplier selectedSupplier = null;

    public void setFacade(InventoryFacade facade) {
        this.facade = facade;
        loadTable();
    }

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colKontak.setCellValueFactory(new PropertyValueFactory<>("kontak"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        tblSupplier.setOnMouseClicked(event -> {
            Supplier s = tblSupplier.getSelectionModel().getSelectedItem();
            if (s != null) {
                selectedSupplier = s;
                txtNama.setText(s.getNama());
                txtKontak.setText(s.getKontak());
                txtAlamat.setText(s.getAlamat());
            }
        });
    }

    @FXML
    private void handleSimpan() {
        try {
            Supplier s = new Supplier();
            s.setNama(txtNama.getText().trim());
            s.setKontak(txtKontak.getText().trim());
            s.setAlamat(txtAlamat.getText().trim());

            facade.addSupplier(s);

            lblStatus.setText("Supplier berhasil ditambahkan");

            loadTable();
            clearForm();

        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            if (selectedSupplier == null) {
                lblStatus.setText("Pilih supplier dulu dari tabel");
                return;
            }

            selectedSupplier.setNama(txtNama.getText().trim());
            selectedSupplier.setKontak(txtKontak.getText().trim());
            selectedSupplier.setAlamat(txtAlamat.getText().trim());

            facade.updateSupplier(selectedSupplier);

            lblStatus.setText("Supplier berhasil diperbarui");

            loadTable();
            clearForm();

        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            if (selectedSupplier == null) {
                lblStatus.setText("Pilih supplier terlebih dahulu");
                return;
            }

            facade.deleteSupplier(selectedSupplier.getId());
            lblStatus.setText("Supplier berhasil dihapus");

            loadTable();
            clearForm();

        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    private void loadTable() {
        try {
            if (facade == null) return;

            List<Supplier> data = facade.listSupplier();
            tblSupplier.getItems().setAll(data);

        } catch (Exception e) {
            lblStatus.setText("Gagal memuat data supplier: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtNama.clear();
        txtKontak.clear();
        txtAlamat.clear();
        selectedSupplier = null;
    }
}
