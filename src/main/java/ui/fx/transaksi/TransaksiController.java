package ui.fx.transaksi;

import facade.InventoryFacade;
import interfaces.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import models.Transaksi;

import java.util.Date;
import java.util.List;

public class TransaksiController implements Observer{

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
        this.facade.registerTransaksiObserver(this);
        loadTransaksiTable();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(col ->
            new ReadOnlyObjectWrapper<>(tblTransaksi.getItems().indexOf(col.getValue()) + 1)
        );

        colBarangId.setCellValueFactory(new PropertyValueFactory<>("barangId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colCatatan.setCellValueFactory(new PropertyValueFactory<>("catatan"));

        tblTransaksi.getItems().addListener((ListChangeListener<Transaksi>) c -> {
            tblTransaksi.refresh();
        });
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
                return;
            }
            List<Transaksi> data = facade.listTransaksi();
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
    
    @Override
    public void update() {
        loadTransaksiTable();
    }
}
