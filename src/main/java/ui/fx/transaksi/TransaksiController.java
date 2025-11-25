package ui.fx.transaksi;

import facade.InventoryFacade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleObjectProperty;
import models.Transaksi;

import java.util.Date;
import java.util.List;

public class TransaksiController {

    @FXML
    private TextField txtBarangId;

    @FXML
    private TextField txtQty;

    @FXML
    private ComboBox<String> cbJenis;

    @FXML
    private TextField txtCatatan;

    @FXML
    private TableView<Transaksi> tblTransaksi;

    @FXML
    private TableColumn<Transaksi, Integer> colId;

    @FXML
    private TableColumn<Transaksi, Integer> colBarangId;

    @FXML
    private TableColumn<Transaksi, Integer> colQty;

    @FXML
    private TableColumn<Transaksi, String> colJenis;

    @FXML
    private TableColumn<Transaksi, Date> colTanggal;

    @FXML
    private TableColumn<Transaksi, String> colCatatan;

    @FXML
    private Label lblStatus;

    private InventoryFacade facade;

    public void setFacade(InventoryFacade facade) {
        this.facade = facade;
        System.out.println("[DEBUG] TransaksiController.setFacade dipanggil");
        loadTransaksiTable();
    }

    @FXML
    public void initialize() {
        // Isi combo box kalau mau dari kode juga (opsional karena kamu sudah isi dari FXML)
        if (cbJenis.getItems().isEmpty()) {
            cbJenis.getItems().setAll("MASUK", "KELUAR");
        }

        // Binding kolom ke property di models.Transaksi (getId, getBarangId, dst.)
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBarangId.setCellValueFactory(new PropertyValueFactory<>("barangId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));

        colTanggal.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTanggal())
        );

        colCatatan.setCellValueFactory(new PropertyValueFactory<>("catatan"));
    }

    @FXML
    private void handleSimpan() {
        try {
            int barangId = Integer.parseInt(txtBarangId.getText().trim());
            int qty = Integer.parseInt(txtQty.getText().trim());
            String jenis = cbJenis.getValue();
            String catatan = txtCatatan.getText();
            Integer userId = 1;

            if ("MASUK".equalsIgnoreCase(jenis)) {
                facade.addTransaksiMasuk(barangId, qty, userId, catatan);
            } else if ("KELUAR".equalsIgnoreCase(jenis)) {
                facade.addTransaksiKeluar(barangId, qty, userId, catatan);
            } else {
                throw new IllegalArgumentException("Jenis transaksi belum dipilih");
            }

            lblStatus.setText("Transaksi berhasil disimpan.");
            loadTransaksiTable();
            clearForm();

        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadTransaksiTable() {
        try {
            if (facade == null) {
                System.out.println("[DEBUG] facade masih null, skip loadTransaksiTable");
                return;
            }
            List<Transaksi> data = facade.listTransaksi();
            System.out.println("[DEBUG] loadTransaksiTable, dapat data = " + data.size());
            tblTransaksi.getItems().setAll(data);
        } catch (Exception e) {
            lblStatus.setText("Gagal memuat transaksi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtBarangId.clear();
        txtQty.clear();
        cbJenis.getSelectionModel().clearSelection();
        txtCatatan.clear();
    }
}
