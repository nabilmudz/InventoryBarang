package services.supplier;

import dao.supplier.SupplierDAO;
import dao.supplier.SupplierDAOImpl;
import exception.InventoryException;
import exception.ValidationException;
import models.Supplier;

import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO supplierDAO = new SupplierDAOImpl();

    @Override
    public void create(Supplier s) throws ValidationException, InventoryException {

        if (s.getNama() == null || s.getNama().trim().isEmpty()) {
            throw new ValidationException("Nama supplier tidak boleh kosong");
        }

        if (s.getKontak() == null || s.getKontak().trim().isEmpty()) {
            throw new ValidationException("Kontak supplier tidak boleh kosong");
        }

        try {
            Supplier existing = supplierDAO.findByKontak(s.getNama(), s.getKontak());
            if (existing != null) {
                throw new ValidationException("Supplier dengan nama & kontak tersebut sudah ada");
            }

            supplierDAO.insert(s);

        } catch (Exception e) {
            throw new InventoryException("Gagal membuat supplier", e);
        }
    }

    @Override
    public void update(Supplier s) throws ValidationException, InventoryException {

        if (s.getId() <= 0) {
            throw new ValidationException("ID supplier tidak valid");
        }

        if (s.getNama() == null || s.getNama().trim().isEmpty()) {
            throw new ValidationException("Nama supplier tidak boleh kosong");
        }

        if (s.getKontak() == null || s.getKontak().trim().isEmpty()) {
            throw new ValidationException("Kontak supplier tidak boleh kosong");
        }

        try {
            Supplier existing = supplierDAO.findByKontak(s.getNama(), s.getKontak());
            if (existing != null && existing.getId() != s.getId()) {
                throw new ValidationException("Nama & kontak supplier sudah digunakan supplier lain");
            }

            supplierDAO.update(s);

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InventoryException("Gagal memperbarui supplier", e);
        }
    }

    @Override
    public void delete(int id) throws ValidationException, InventoryException {

        if (id <= 0) {
            throw new ValidationException("ID supplier tidak valid");
        }

        try {
            if (supplierDAO.existInBarang(id)) {
                throw new ValidationException("Supplier masih dipakai di data Barang");
            }

            if (supplierDAO.existInTransaksi(id)) {
                throw new ValidationException("Supplier masih dipakai di Transaksi");
            }

            supplierDAO.delete(id);

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InventoryException("Gagal menghapus supplier", e);
        }
    }

    @Override
    public Supplier getById(int id) throws ValidationException, InventoryException {
        if (id <= 0) {
            throw new ValidationException("ID supplier tidak valid");
        }

        try {
            Supplier s = supplierDAO.findById(id);
            if (s == null) {
                throw new ValidationException("Supplier dengan ID " + id + " tidak ditemukan");
            }
            return s;

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InventoryException("Gagal mengambil supplier berdasarkan ID", e);
        }
    }

    @Override
    public List<Supplier> getAll() throws InventoryException {
        try {
            return supplierDAO.findAll();
        } catch (Exception e) {
            throw new InventoryException("Gagal mengambil seluruh data supplier", e);
        }
    }
}