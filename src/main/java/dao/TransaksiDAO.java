package main.java.dao;

import java.util.List;

import main.java.models.Transaksi;

public interface TransaksiDAO {
    void create(Transaksi transaksi) throws Exception;
    List<Transaksi> findAll() throws Exception;
}
