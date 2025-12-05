package services.transaksi;

import dao.transaksi.TransaksiDAO;
import dao.transaksi.TransaksiDAOImpl;
import dao.barang.BarangDAO;
import dao.barang.BarangDAOImpl;
import exception.ValidationException;
import interfaces.Observer;
import models.Barang;
import models.Transaksi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransaksiServiceImpl implements TransaksiService {
    private final TransaksiDAO transaksiDAO;
    private final BarangDAO barangDAO;
    private final List<Observer> observers = new ArrayList<>();

    public TransaksiServiceImpl() {
        this(new TransaksiDAOImpl(), new BarangDAOImpl());
    }

    public TransaksiServiceImpl(TransaksiDAO transaksiDAO, BarangDAO barangDAO) {
        this.transaksiDAO = transaksiDAO;
        this.barangDAO = barangDAO;
    }

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

        try {
            // ✅ pakai findById (dari CrudDAO)
            Barang barang = barangDAO.findById(barangId);
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

            transaksiDAO.insert(t);

            int stokBaru = barang.getStok() + qty;
            barang.setStok(stokBaru);

            // ✅ update(Barang b) throws Exception
            barangDAO.update(barang);

            notifyObservers();
            return t;

        } catch (ValidationException e) {
            // langsung lempar ulang
            throw e;
        } catch (Exception e) {
            // wrap ke ValidationException (sesuai signaturenya)
            throw new ValidationException("Gagal menyimpan transaksi MASUK: " + e.getMessage());
        }
    }

    @Override
    public Transaksi createKeluar(int barangId, int qty, Integer createdBy, String catatan)
            throws ValidationException {

        if (qty <= 0) {
            throw new ValidationException("Qty tidak boleh <= 0");
        }

        try {
            Barang barang = barangDAO.findById(barangId);
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

            transaksiDAO.insert(t);

            int stokBaru = barang.getStok() - qty;
            barang.setStok(stokBaru);
            barangDAO.update(barang);

            notifyObservers();
            return t;

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("Gagal menyimpan transaksi KELUAR: " + e.getMessage());
        }
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
