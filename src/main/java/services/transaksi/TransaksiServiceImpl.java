package services.transaksi;

// import dao.barang.BarangDAO;
// import dao.barang.BarangDAOImpl;
import dao.transaksi.TransaksiDAO;
import dao.transaksi.TransaksiDAOImpl;
// import models.Barang;
import models.Transaksi;

import java.util.Date;
import java.util.List;

// DEMO
public class TransaksiServiceImpl implements TransaksiService {

    private final TransaksiDAO transaksiDAO = new TransaksiDAOImpl();

    @Override
    public void createMasuk(int barangId, int qty, Integer createdBy, String catatan) throws Exception {
        if (qty <= 0) {
            throw new Exception("Qty tidak boleh <= 0");
        }

        // TODO: nanti tambahkan pengecekan barang & update stok
        // Barang barang = barangDAO.findById(barangId);
        // if (barang == null) throw new Exception(...);

        Transaksi t = new Transaksi(
                barangId,
                qty,
                Transaksi.JENIS_MASUK,
                new Date(),
                createdBy,
                catatan
        );

        transaksiDAO.insert(t);
    }

    @Override
    public void createKeluar(int barangId, int qty, Integer createdBy, String catatan) throws Exception {
        if (qty <= 0) {
            throw new Exception("Qty tidak boleh <= 0");
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

        transaksiDAO.insert(t);
    }

    @Override
    public void create(Transaksi t) throws Exception {
        if (t.getQty() <= 0) {
            throw new Exception("Qty tidak boleh <= 0");
        }

        // TODO: nanti tambahkan pengecekan barang + stok
        // Barang barang = barangDAO.findById(t.getBarangId());
        // if (barang == null) ...
        // if JENIS_KELUAR & stok < qty → error

        if (!Transaksi.JENIS_MASUK.equalsIgnoreCase(t.getJenis())
                && !Transaksi.JENIS_KELUAR.equalsIgnoreCase(t.getJenis())) {
            throw new Exception("Jenis transaksi tidak valid: " + t.getJenis());
        }

        transaksiDAO.insert(t);
    }

    @Override
    public List<Transaksi> getAll() throws Exception {
        return transaksiDAO.findAll();
    }
}

// WITH BARANG
// public class TransaksiServiceImpl implements TransaksiService {

//     private final TransaksiDAO transaksiDAO = new TransaksiDAOImpl();
//     private final BarangDAO barangDAO       = new BarangDAOImpl();

//     @Override
//     public void createMasuk(int barangId, int qty, Integer createdBy, String catatan) throws Exception {
//         if (qty <= 0) {
//             throw new Exception("Qty tidak boleh <= 0");
//         }

//         Barang barang = barangDAO.findById(barangId);
//         if (barang == null) {
//             throw new Exception("Barang dengan ID " + barangId + " tidak ditemukan");
//         }

//         // buat transaksi MASUK
//         Transaksi t = new Transaksi(
//                 barangId,
//                 qty,
//                 Transaksi.JENIS_MASUK,
//                 new Date(),
//                 createdBy,
//                 catatan
//         );

//         // simpan transaksi
//         transaksiDAO.insert(t);

//         // update stok barang (stok bertambah)
//         int stokBaru = barang.getStok() + qty;
//         barang.setStok(stokBaru);
//         barangDAO.update(barang);

//         // nanti di sini bisa panggil Observer (notifyStockChanged(barang), dll.)
//     }

//     @Override
//     public void createKeluar(int barangId, int qty, Integer createdBy, String catatan) throws Exception {
//         if (qty <= 0) {
//             throw new Exception("Qty tidak boleh <= 0");
//         }

//         Barang barang = barangDAO.findById(barangId);
//         if (barang == null) {
//             throw new Exception("Barang dengan ID " + barangId + " tidak ditemukan");
//         }

//         if (barang.getStok() < qty) {
//             throw new Exception("Stok tidak cukup. Stok saat ini: " + barang.getStok());
//         }

//         // buat transaksi KELUAR
//         Transaksi t = new Transaksi(
//                 barangId,
//                 qty,
//                 Transaksi.JENIS_KELUAR,
//                 new Date(),
//                 createdBy,
//                 catatan
//         );

//         // simpan transaksi
//         transaksiDAO.insert(t);

//         // update stok barang (stok berkurang)
//         int stokBaru = barang.getStok() - qty;
//         barang.setStok(stokBaru);
//         barangDAO.update(barang);

//         // nanti juga bisa notify observer
//     }

//     // method lama, kalau mau tetap dipakai (misal dari Facade yang lama)
//     @Override
//     public void create(Transaksi t) throws Exception {
//         if (t.getQty() <= 0) {
//             throw new Exception("Qty tidak boleh <= 0");
//         }

//         // ambil barang
//         Barang barang = barangDAO.findById(t.getBarangId());
//         if (barang == null) {
//             throw new Exception("Barang dengan ID " + t.getBarangId() + " tidak ditemukan");
//         }

//         if (Transaksi.JENIS_MASUK.equalsIgnoreCase(t.getJenis())) {
//             // stok naik
//             transaksiDAO.insert(t);
//             barang.setStok(barang.getStok() + t.getQty());
//             barangDAO.update(barang);
//         } else if (Transaksi.JENIS_KELUAR.equalsIgnoreCase(t.getJenis())) {
//             // stok turun, cek stok dulu
//             if (barang.getStok() < t.getQty()) {
//                 throw new Exception("Stok tidak cukup. Stok saat ini: " + barang.getStok());
//             }
//             transaksiDAO.insert(t);
//             barang.setStok(barang.getStok() - t.getQty());
//             barangDAO.update(barang);
//         } else {
//             throw new Exception("Jenis transaksi tidak valid: " + t.getJenis());
//         }
//     }

//     @Override
//     public List<Transaksi> getAll() throws Exception {
//         return transaksiDAO.findAll();
//     }
// }
