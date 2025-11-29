package dao.barang;

import models.Barang;
import java.util.List;

public interface BarangDAO {

    boolean insert(Barang barang);

    boolean update(Barang barang);

    boolean delete(int id);

    Barang getById(int id);

    List<Barang> getAll();
}
