package services;

import java.util.List;

import models.Transaksi;

public interface TransaksiService {
    void createTransaksi(Transaksi t) throws Exception;
    List<Transaksi> getAll() throws Exception;
}
