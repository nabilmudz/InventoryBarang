package services.transaksi;

import dao.transaksi.TransaksiDAO;
import dao.transaksi.TransaksiDAOImpl;
import exception.ValidationException;
import models.Transaksi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import interfaces.Observer;

// DEMO
public class TransaksiServiceImpl implements TransaksiService {

    private final TransaksiDAO transaksiDAO = new TransaksiDAOImpl();
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void createMasuk(int barangId, int qty, Integer createdBy, String catatan)
            throws ValidationException {
        if (qty <= 0) {
            throw new ValidationException("Qty tidak boleh <= 0");
        }

        Transaksi t = new Transaksi(
                barangId,
                qty,
                Transaksi.JENIS_MASUK,
                new Date(),
                createdBy,
                catatan
        );

        try {
            transaksiDAO.insert(t);
        } catch (Exception e) {
            // kalau mau: wrap jadi runtime / InventoryException
            throw new ValidationException("Gagal menyimpan transaksi MASUK: " + e.getMessage());
        }

        notifyObservers();
    }

    @Override
    public void createKeluar(int barangId, int qty, Integer createdBy, String catatan) throws ValidationException {
        if (qty <= 0) {
            throw new ValidationException("Qty tidak boleh <= 0");
        }

        // TODO: nanti tambahkan pengecekan stok barang
        // Barang barang = barangDAO.findById(barangId);
        // if (barang == null) throw new Exception(...);
        // if (barang.getStok() < qty) throw new Exception("Stok tidak cukup");

        Transaksi t = new Transaksi(
                barangId,
                qty,
                Transaksi.JENIS_KELUAR,
                new Date(),
                createdBy,
                catatan
        );

        try {
            transaksiDAO.insert(t);
        } catch (Exception e) {
            throw new ValidationException("Gagal menyimpan transaksi MASUK: " + e.getMessage());
        }
        notifyObservers();
    }

    @Override
    public List<Transaksi> getAll() {
        try {
            return transaksiDAO.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
