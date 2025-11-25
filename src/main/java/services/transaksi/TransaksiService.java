package services.transaksi;

import java.util.List;

import models.Transaksi;

public interface TransaksiService {
    void create(Transaksi t) throws Exception;
    List<Transaksi> getAll() throws Exception;
}
