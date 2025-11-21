package main.java.services;

import java.util.List;

import main.java.models.Transaksi;

public interface TransaksiService {
    void createTransaksi(Transaksi t) throws Exception;
    List<Transaksi> getAll() throws Exception;
}
