package services.transaksi;

import java.util.List;
import models.Transaksi;

public interface TransaksiService {
    void createMasuk(int barangId, int qty, Integer createdBy, String catatan) throws Exception;
    void createKeluar(int barangId, int qty, Integer createdBy, String catatan) throws Exception;

    // kalau mau tetap pakai
    void create(Transaksi t) throws Exception;

    List<Transaksi> getAll() throws Exception;
}
