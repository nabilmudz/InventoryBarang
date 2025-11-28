package services.supplier;

import models.Supplier;
import java.util.List;

public interface SupplierService {
    void create(Supplier s) throws Exception;
    void update(Supplier s) throws Exception;
    void delete(int id) throws Exception;

    List<Supplier> getAll() throws Exception;

    Supplier getById(int id) throws Exception;
}
