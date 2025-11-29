package facade;

import models.Supplier;
import models.Transaksi;
import services.supplier.SupplierService;
import services.supplier.SupplierServiceImpl;
import services.transaksi.TransaksiService;
import services.transaksi.TransaksiServiceImpl;

import java.util.List;

public class InventoryFacade {

    private final SupplierService supplierService = new SupplierServiceImpl();
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

    public void addSupplier(Supplier s) throws Exception {
        supplierService.create(s);
    }

    public void updateSupplier(Supplier s) throws Exception {
        supplierService.update(s);
    }

    public void deleteSupplier(int id) throws Exception {
        supplierService.delete(id);
    }

    public Supplier getSupplierById(int id) throws Exception {
        return supplierService.getById(id);
    }

    public List<Supplier> listSupplier() throws Exception {
        return supplierService.getAll();
    }
}
