package services.supplier;

import interfaces.Subject;
import models.Supplier;
import java.util.List;

public interface SupplierService extends Subject {

    void create(Supplier supplier) throws Exception;

    void update(Supplier supplier) throws Exception;

    void delete(int id) throws Exception;

    Supplier findById(int id) throws Exception;

    List<Supplier> findAll() throws Exception;
}
