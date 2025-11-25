package services.transaksi;

import dao.transaksi.TransaksiDAO;
import dao.transaksi.TransaksiDAOImpl;
import models.Transaksi;

import java.util.List;

public class TransaksiServiceImpl implements TransaksiService {

    private final TransaksiDAO transaksiDAO = new TransaksiDAOImpl();

    @Override
    public void create(Transaksi t) throws Exception {
        if (t.getQty() <= 0) throw new Exception("Qty tidak boleh <= 0");
        transaksiDAO.insert(t);
    }

    @Override
    public List<Transaksi> getAll() throws Exception {
        return transaksiDAO.findAll();
    }
}

