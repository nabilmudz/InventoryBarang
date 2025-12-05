package services.barang;

import dao.barang.BarangDAO;
import dao.barang.BarangDAOImpl;
import models.Barang;

import java.util.List;

public class BarangServiceImpl implements BarangService {

    private BarangDAO barangDAO;

    public BarangServiceImpl() {
        this.barangDAO = new BarangDAOImpl();
    }

    public BarangServiceImpl(BarangDAO barangDAO) {
        this.barangDAO = barangDAO;
        this.barangDAO = barangDAO;
    }

    @Override
    public void create(Barang b) throws Exception {
        if (b.getNama() == null || b.getNama().trim().isEmpty()) {
            throw new Exception("Nama barang tidak boleh kosong");
        }

        if (b.getStok() < 0) {
            throw new Exception("Stok barang tidak boleh negatif");
        }

        if (b.getHarga() < 0) {
            throw new Exception("Harga barang tidak boleh negatif");
        }

        if (b.getSupplierId() <= 0) {
            throw new Exception("Supplier ID tidak valid");
        }

        Barang existing = barangDAO.findByNama(b.getNama());
        if (existing != null) {
            throw new Exception("Barang dengan nama tersebut sudah ada");
        }

        barangDAO.insert(b);
    }

    @Override
    public void update(Barang b) throws Exception {
        if (b.getId() <= 0) {
            throw new Exception("ID barang tidak valid");
        }

        if (b.getNama() == null || b.getNama().trim().isEmpty()) {
            throw new Exception("Nama barang tidak boleh kosong");
        }

        if (b.getStok() < 0) {
            throw new Exception("Stok barang tidak boleh negatif");
        }

        if (b.getHarga() < 0) {
            throw new Exception("Harga barang tidak boleh negatif");
        }

        if (b.getSupplierId() <= 0) {
            throw new Exception("Supplier ID tidak valid");
        }

        Barang existing = barangDAO.findByNama(b.getNama());
        if (existing != null && existing.getId() != b.getId()) {
            throw new Exception("Nama barang sudah digunakan barang lain");
        }

        barangDAO.update(b);
    }

    @Override
    public void delete(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID barang tidak valid");
        }

        if (barangDAO.existInTransaksi(id)) {
            throw new Exception("Barang masih dipakai di Transaksi");
        }

        barangDAO.delete(id);
    }

    @Override
    public Barang getById(int id) throws Exception {
        Barang b = barangDAO.findById(id);
        if (b == null) {
            throw new Exception("Barang dengan ID " + id + " tidak ditemukan");
        }
        return b;
    }

    @Override
    public List<Barang> getAll() throws Exception {
        return barangDAO.findAll();
    }
}