package main.java.facade;

import java.util.List;

import main.java.models.Transaksi;
import main.java.services.TransaksiService;
import main.java.services.impl.TransaksiServiceImpl;

public class InventoryFacade {

    private final TransaksiService transaksiService = new TransaksiServiceImpl();

    public void createTransaksi(Transaksi t) throws Exception {
        transaksiService.createTransaksi(t);
    }

    public List<Transaksi> getAllTransaksi() throws Exception {
        return transaksiService.getAll();
    }
}
