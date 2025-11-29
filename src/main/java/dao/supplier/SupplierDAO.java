package dao.supplier;

import dao.CrudDAO;
import models.Supplier;

public interface SupplierDAO extends CrudDAO<Supplier> {
    Supplier findByKontak(String nama, String kontak) throws Exception;

    boolean existInBarang(int supplierId) throws Exception;
    boolean existInTransaksi(int supplierId) throws Exception;
}