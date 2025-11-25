package dao;

import java.util.List;

import models.Transaksi;

public interface TransaksiDAO {
    void create(Transaksi transaksi) throws Exception;
    List<Transaksi> findAll() throws Exception;
}
