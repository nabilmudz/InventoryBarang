package main.java.services.impl;

import java.util.List;

import main.java.dao.TransaksiDAO;
import main.java.dao.impl.TransaksiDAOImpl;
import main.java.models.Transaksi;
import main.java.services.TransaksiService;

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
