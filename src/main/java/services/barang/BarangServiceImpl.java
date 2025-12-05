package services.barang;

import dao.barang.BarangDAO;
import dao.barang.BarangDAOImpl;
import models.Barang;

import java.util.List;

public class BarangServiceImpl implements BarangService {

    private final BarangDAO barangDAO;

    public BarangServiceImpl() {
        this.barangDAO = new BarangDAOImpl(); 
    }

    public BarangServiceImpl(BarangDAO barangDAO) {
        this.barangDAO = barangDAO;
    }

    @Override
    public Barang getBarangById(int id) {
        return barangDAO.getById(id);
    }

    @Override
    public List<Barang> getAllBarang() {
        return barangDAO.getAll();
    }

    @Override
    public void addBarang(Barang barang) {
        if (barang.getNama() == null || barang.getNama().isEmpty()) {
            throw new IllegalArgumentException("Nama barang tidak boleh kosong");
        }
        barangDAO.insert(barang);
    }

    @Override
    public void updateBarang(Barang barang) {
        barangDAO.update(barang);
    }

    @Override
    public void deleteBarang(int id) {
        barangDAO.delete(id);
    }
}
