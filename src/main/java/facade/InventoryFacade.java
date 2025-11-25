package facade;

import java.util.List;

import models.Transaksi;
import services.TransaksiService;
import services.impl.TransaksiServiceImpl;

public class InventoryFacade {

    private final TransaksiService transaksiService = new TransaksiServiceImpl();

    public void createTransaksi(Transaksi t) throws Exception {
        transaksiService.createTransaksi(t);
    }

    public List<Transaksi> getAllTransaksi() throws Exception {
        return transaksiService.getAll();
    }
}
