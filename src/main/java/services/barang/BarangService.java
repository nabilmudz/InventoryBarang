package services.barang;

import models.Barang;
import java.util.List;

public interface BarangService {

    Barang getBarangById(int id);

    List<Barang> getAllBarang();

    void addBarang(Barang barang);

    void updateBarang(Barang barang);

    void deleteBarang(int id);
}
