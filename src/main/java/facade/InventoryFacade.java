package facade;

import models.Transaksi;
import services.transaksi.TransaksiService;
import services.transaksi.TransaksiServiceImpl;

import java.util.List;

public class InventoryFacade {

    private final TransaksiService transaksiService = new TransaksiServiceImpl();

    public void addTransaksi(Transaksi t) throws Exception {
        transaksiService.create(t);
    }

    public List<Transaksi> listTransaksi() throws Exception {
        return transaksiService.getAll();
    }
}

