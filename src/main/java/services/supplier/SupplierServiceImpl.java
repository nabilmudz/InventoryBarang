package services.supplier;

import dao.supplier.SupplierDAO;
import dao.supplier.SupplierDAOImpl;
import models.Supplier;

import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO supplierDAO = new SupplierDAOImpl();

    @Override
    public void create(Supplier s) throws Exception {
        if (s.getNama() == null || s.getNama().trim().isEmpty()) {
            throw new Exception("Nama supplier tidak boleh kosong");
        }

         if (s.getKontak() == null || s.getKontak().trim().isEmpty()) {
            throw new Exception("Kontak supplier tidak boleh kosong");
        }

        Supplier existing = supplierDAO.findByKontak(s.getNama(), s.getKontak());
        if (existing != null) {
            throw new Exception("Supplier dengan nama dan kontak tersebut sudah ada");
        }

        supplierDAO.insert(s);
    }

    @Override
    public void update(Supplier s) throws Exception {
        if (s.getId() <= 0) {
            throw new Exception("ID supplier tidak valid");
        }

        if (s.getNama() == null || s.getNama().trim().isEmpty()) {
            throw new Exception("Nama supplier tidak boleh kosong");
        }

        if (s.getKontak() == null || s.getKontak().trim().isEmpty()) {
            throw new Exception("Kontak supplier tidak boleh kosong");
        }

        Supplier existing = supplierDAO.findByKontak(s.getNama(), s.getKontak());
        if (existing != null && existing.getId() != s.getId()) {
            throw new Exception("Nama & kontak supplier sudah digunakan supplier lain");
        }

        supplierDAO.update(s);
    }

    @Override
    public void delete(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID supplier tidak valid");
        }

        if (supplierDAO.existInBarang(id)) {
            throw new Exception("Supplier masih dipakai di data Barang");
        }

        if (supplierDAO.existInTransaksi(id)) {
            throw new Exception("Supplier masih dipakai di Transaksi");
        }

        supplierDAO.delete(id);
    }

    @Override
    public Supplier getById(int id) throws Exception {
        Supplier s = supplierDAO.findById(id);
        if (s == null) {
            throw new Exception("Supplier dengan ID " + id + " tidak ditemukan");
        }
        return s;
    }

    @Override
    public List<Supplier> getAll() throws Exception {
        return supplierDAO.findAll();
    }
}
