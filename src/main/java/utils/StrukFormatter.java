package utils;

import models.Transaksi;
import java.text.SimpleDateFormat;

public final class StrukFormatter {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private StrukFormatter() {
    }

    public static String format(Transaksi t) {
        StringBuilder sb = new StringBuilder();
        sb.append("======== STRUK TRANSAKSI ========\n");
        sb.append("ID Transaksi : ").append(t.getId() > 0 ? t.getId() : "-").append("\n");
        sb.append("Tanggal      : ")
          .append(t.getTanggal() != null ? sdf.format(t.getTanggal()) : "-")
          .append("\n");
        sb.append("Jenis        : ").append(t.getJenis()).append("\n");
        sb.append("-------------------------------------\n");
        sb.append("Barang ID    : ").append(t.getBarangId()).append("\n");
        sb.append("Qty          : ").append(t.getQty()).append("\n");
        sb.append("-------------------------------------\n");
        sb.append("User         : ")
          .append(t.getCreatedBy() != null ? t.getCreatedBy() : "-")
          .append("\n");
        sb.append("Catatan      : ")
          .append(t.getCatatan() != null ? t.getCatatan() : "-")
          .append("\n");
        sb.append("=============================\n");
        sb.append("   Terima kasih telah bertransaksi   \n");
        sb.append("=============================\n");
        return sb.toString();
    }
}
