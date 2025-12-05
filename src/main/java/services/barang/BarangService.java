package services.barang;

import models.Barang;
import java.util.List;

public interface BarangService {
    void create(Barang b) throws Exception;
    void update(Barang b) throws Exception;
    void delete(int id) throws Exception;

    List<Barang> getAll() throws Exception;
    Barang getById(int id) throws Exception;
}
