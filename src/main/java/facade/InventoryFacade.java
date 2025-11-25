package facade;

import models.Transaksi;
import services.transaksi.TransaksiService;
import services.transaksi.TransaksiServiceImpl;

import java.util.List;

public class InventoryFacade {

    private final TransaksiService transaksiService = new TransaksiServiceImpl();

    // API level tinggi, dipakai UI
    public void addTransaksiMasuk(int barangId, int qty, Integer userId, String catatan) throws Exception {
        transaksiService.createMasuk(barangId, qty, userId, catatan);
    }

    public void addTransaksiKeluar(int barangId, int qty, Integer userId, String catatan) throws Exception {
        transaksiService.createKeluar(barangId, qty, userId, catatan);
    }

    // kalau mau tetap pakai versi objek (seperti di App lama)
    public void addTransaksi(Transaksi t) throws Exception {
        transaksiService.create(t);
    }

    public List<Transaksi> listTransaksi() throws Exception {
        return transaksiService.getAll();
    }
}
