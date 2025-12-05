package dao.barang;

import dao.CrudDAO;
import models.Barang;

public interface BarangDAO extends CrudDAO<Barang> {
    Barang findByNama(String nama) throws Exception;
    boolean existInTransaksi(int barangId) throws Exception;
}
