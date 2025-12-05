package ui.fx.transaksi;

import exception.ValidationException;
import facade.InventoryFacade;
import interfaces.Observer;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleObjectProperty;
import models.Transaksi;
import utils.StrukFormatter;
import java.util.Date;
import java.util.List;

public class TransaksiController implements Observer {

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

    @FXML
    private TextArea txtStruk;

    private InventoryFacade facade;

    public void setFacade(InventoryFacade facade) {
        this.facade = facade;
        this.facade.registerTransaksiObserver(this);
        loadTransaksiTable();
    }

    @FXML
    public void initialize() {
        if (cbJenis.getItems().isEmpty()) {
            cbJenis.getItems().setAll("MASUK", "KELUAR");
        }
        cbJenis.getSelectionModel().selectFirst();

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
            int qty      = Integer.parseInt(txtQty.getText().trim());
            String jenis = cbJenis.getValue();
            String catatan = txtCatatan.getText();
            Integer userId = 1;

            Transaksi t;

            if ("MASUK".equalsIgnoreCase(jenis)) {
                t = facade.addTransaksiMasuk(barangId, qty, userId, catatan);
            } else if ("KELUAR".equalsIgnoreCase(jenis)) {
                t = facade.addTransaksiKeluar(barangId, qty, userId, catatan);
            } else {
                throw new ValidationException("Jenis transaksi belum dipilih");
            }

            txtStruk.setText(StrukFormatter.format(t));

            lblStatus.setText("Transaksi berhasil disimpan!");
            loadTransaksiTable();
            clearForm();

        } catch (ValidationException e) {
            lblStatus.setText(e.getMessage());
        } catch (NumberFormatException e) {
            lblStatus.setText("ID Barang dan Qty harus berupa angka");
        } catch (Exception e) {
            lblStatus.setText("Gagal menyimpan transaksi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrintStruk() {
        try {
            if (txtStruk.getText() == null || txtStruk.getText().isBlank()) {
                lblStatus.setText("Belum ada struk untuk dicetak");
                return;
            }

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null &&
                job.showPrintDialog(tblTransaksi.getScene().getWindow())) {

                boolean success = job.printPage(txtStruk);
                if (success) {
                    job.endJob();
                    lblStatus.setText("Struk berhasil dikirim ke printer");
                } else {
                    lblStatus.setText("Gagal mencetak struk");
                }
            }
        } catch (Exception e) {
            lblStatus.setText("Error print: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadTransaksiTable() {
        try {
            if (facade == null) return;
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
        cbJenis.getSelectionModel().selectFirst();
        txtCatatan.clear();
    }

    @Override
    public void update() {
        loadTransaksiTable();
    }
}
