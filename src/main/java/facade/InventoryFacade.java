package facade;

import models.Supplier;
import models.Transaksi;
import models.Barang;
import services.supplier.SupplierService;
import services.supplier.SupplierServiceImpl;
import services.transaksi.TransaksiService;
import services.transaksi.TransaksiServiceImpl;
import interfaces.Observer;
import services.barang.BarangService;
import services.barang.BarangServiceImpl;

import java.util.List;

import exception.ValidationException;

public class InventoryFacade {

    private final SupplierService supplierService = new SupplierServiceImpl();
    private final TransaksiService transaksiService = new TransaksiServiceImpl();
    private final BarangService barangService = new BarangServiceImpl();

    // Barang methods
    public void addBarang(Barang b) throws Exception {
        barangService.addBarang(b);
    }

    public void updateBarang(Barang b) throws Exception {
        barangService.updateBarang(b);
    }

    public void deleteBarang(int id) throws Exception {
        barangService.deleteBarang(id);
    }

    public Barang getBarangById(int id) throws Exception {
        return barangService.getBarangById(id);
    }

    public List<Barang> getAllBarang() throws Exception {
        return barangService.getAllBarang();
    }

    // Transaksi methods
    public Transaksi addTransaksiMasuk(int barangId, int qty, Integer userId, String catatan)
        throws ValidationException {
    return transaksiService.createMasuk(barangId, qty, userId, catatan);
    }

    public Transaksi addTransaksiKeluar(int barangId, int qty, Integer userId, String catatan)
            throws ValidationException {
        return transaksiService.createKeluar(barangId, qty, userId, catatan);
    }

    public List<Transaksi> listTransaksi() throws Exception {
        return transaksiService.getAll();
    }

    // Supplier methods
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
    
    public void registerTransaksiObserver(Observer o) {
        transaksiService.registerObserver(o);
    }

    public void removeTransaksiObserver(Observer o) {
        transaksiService.removeObserver(o);
    }
}
