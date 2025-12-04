package ui.fx.laporan;

import facade.InventoryFacade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Barang;

import java.time.LocalDate;
import java.util.List;

public class LaporanController {

    @FXML
    private ComboBox<String> cbTipeLaporan;

    @FXML
    private DatePicker dpFromDate;

    @FXML
    private DatePicker dpToDate;

    @FXML
    private Label lblTotalBarangReport;

    @FXML
    private Label lblTotalNilaiStok;

    @FXML
    private Label lblTotalTransaksiReport;

    @FXML
    private TableView<String> tableReport;

    @FXML
    private Label lblReportStatus;

    private InventoryFacade facade;

    public void setFacade(InventoryFacade facade) {
        this.facade = facade;
        loadReport();
    }

    @FXML
    public void initialize() {
        if (cbTipeLaporan.getItems().isEmpty()) {
            cbTipeLaporan.getItems().setAll("Semua", "Stok Barang", "Transaksi", "Supplier");
            cbTipeLaporan.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleFilter() {
        try {
            loadReport();
            lblReportStatus.setText("Filter diterapkan");
        } catch (Exception e) {
            lblReportStatus.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleExportExcel() {
        lblReportStatus.setText("Fitur export Excel sedang dikembangkan");
    }

    @FXML
    private void handleExportPDF() {
        lblReportStatus.setText("Fitur export PDF sedang dikembangkan");
    }

    @FXML
    private void handlePrint() {
        lblReportStatus.setText("Fitur cetak sedang dikembangkan");
    }

    @FXML
    private void handleRefresh() {
        try {
            dpFromDate.setValue(null);
            dpToDate.setValue(null);
            cbTipeLaporan.getSelectionModel().selectFirst();
            loadReport();
            lblReportStatus.setText("Data diperbarui");
        } catch (Exception e) {
            lblReportStatus.setText("Error: " + e.getMessage());
        }
    }

    private void loadReport() {
        try {
            if (facade == null) return;

            List<Barang> barangList = facade.getAllBarang();
            
            int totalBarang = barangList.size();
            double totalNilaiStok = barangList.stream()
                    .mapToDouble(b -> b.getStok() * b.getHarga())
                    .sum();
            int totalTransaksi = facade.listTransaksi().size();

            lblTotalBarangReport.setText(String.valueOf(totalBarang));
            lblTotalNilaiStok.setText(String.format("Rp %,.0f", totalNilaiStok));
            lblTotalTransaksiReport.setText(String.valueOf(totalTransaksi));

        } catch (Exception e) {
            lblReportStatus.setText("Gagal memuat laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}