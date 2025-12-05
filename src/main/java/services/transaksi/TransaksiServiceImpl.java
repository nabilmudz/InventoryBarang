package services.transaksi;

import dao.transaksi.TransaksiDAO;
import dao.transaksi.TransaksiDAOImpl;

import dao.barang.BarangDAO;
import dao.barang.BarangDAOImpl;

import exception.ValidationException;
import models.Barang;
import models.Transaksi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import interfaces.Observer;

public class TransaksiServiceImpl implements TransaksiService {

    private final TransaksiDAO transaksiDAO = new TransaksiDAOImpl();
    private final BarangDAO barangDAO       = new BarangDAOImpl();   // <--- tambah ini
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
    public Transaksi createMasuk(int barangId, int qty, Integer createdBy, String catatan)
            throws ValidationException {

        if (qty <= 0) {
            throw new ValidationException("Qty tidak boleh <= 0");
        }

        Barang barang = barangDAO.getById(barangId);
        if (barang == null) {
            throw new ValidationException("Barang dengan ID " + barangId + " tidak ditemukan");
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

            int stokBaru = barang.getStok() + qty;
            barang.setStok(stokBaru);
            barangDAO.update(barang);

        } catch (Exception e) {
            throw new ValidationException("Gagal menyimpan transaksi MASUK: " + e.getMessage());
        }

        notifyObservers();
        return t;
    }

    @Override
    public Transaksi createKeluar(int barangId, int qty, Integer createdBy, String catatan)
            throws ValidationException {

        if (qty <= 0) {
            throw new ValidationException("Qty tidak boleh <= 0");
        }

        Barang barang = barangDAO.getById(barangId);
        if (barang == null) {
            throw new ValidationException("Barang dengan ID " + barangId + " tidak ditemukan");
        }

        if (barang.getStok() < qty) {
            throw new ValidationException(
                    "Stok tidak cukup. Stok saat ini: " + barang.getStok()
            );
        }

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

            int stokBaru = barang.getStok() - qty;
            barang.setStok(stokBaru);
            barangDAO.update(barang);

        } catch (Exception e) {
            throw new ValidationException("Gagal menyimpan transaksi KELUAR: " + e.getMessage());
        }

        notifyObservers();
        return t;
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
