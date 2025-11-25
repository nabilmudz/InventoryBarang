package services.impl;

import java.util.List;

import dao.TransaksiDAO;
import dao.impl.TransaksiDAOImpl;
import models.Transaksi;
import services.TransaksiService;

public class TransaksiServiceImpl implements TransaksiService {

    private final TransaksiDAO transaksiDAO = new TransaksiDAOImpl();

    @Override
    public void createTransaksi(Transaksi t) throws Exception {
        transaksiDAO.create(t);
    }

    @Override
    public List<Transaksi> getAll() throws Exception {
        return transaksiDAO.findAll();
    }
}
