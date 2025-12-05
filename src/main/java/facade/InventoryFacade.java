package facade;

import models.Supplier;
import models.Transaksi;
import models.Barang;

import services.supplier.SupplierService;
import services.supplier.SupplierServiceImpl;

import services.transaksi.TransaksiService;
import services.transaksi.TransaksiServiceImpl;

import services.barang.BarangService;
import services.barang.BarangServiceImpl;
import exception.InventoryException;
import exception.ValidationException;
import dao.supplier.SupplierDAOImpl;

import interfaces.Observer;

import java.util.List;

public class InventoryFacade {

    private final SupplierService supplierService;
    private final TransaksiService transaksiService;
    private final BarangService barangService;

    public InventoryFacade() {
        this.supplierService = new SupplierServiceImpl(new SupplierDAOImpl());
        this.transaksiService = new TransaksiServiceImpl();
        this.barangService = new BarangServiceImpl();
    }
    
    public void addBarang(Barang b) {
        barangService.create(b);
    }

    public void updateBarang(Barang b) throws Exception {
        barangService.update(b);
    }

    public void deleteBarang(int id) throws Exception {
        barangService.delete(id);
    }

    public Barang getBarangById(int id) throws Exception {
        return barangService.getById(id);
    }

    public List<Barang> getAllBarang() throws Exception {
        return barangService.getAll();
    }

    public Transaksi addTransaksiMasuk(int barangId, int qty, Integer userId, String catatan)
        throws ValidationException {
        return transaksiService.createMasuk(barangId, qty, userId, catatan);
    }

    public Transaksi addTransaksiKeluar(int barangId, int qty, Integer userId, String catatan)
            throws ValidationException {
        return transaksiService.createKeluar(barangId, qty, userId, catatan);
    }

    public List<Transaksi> listTransaksi() throws InventoryException {
        return transaksiService.getAll();
    }

    public void registerTransaksiObserver(Observer o) {
        transaksiService.registerObserver(o);
    }

    public void removeTransaksiObserver(Observer o) {
        transaksiService.removeObserver(o);
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
        return supplierService.findById(id);
    }

    public List<Supplier> listSupplier() throws Exception {
        return supplierService.findAll();
    }

    public void registerSupplierObserver(Observer o) {
        supplierService.registerObserver(o);
    }

    public void removeSupplierObserver(Observer o) {
        supplierService.removeObserver(o);
    }
}
