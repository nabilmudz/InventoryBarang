package services.supplier;

import exception.InventoryException;
import exception.ValidationException;
import models.Supplier;
import java.util.List;

public interface SupplierService {

    void create(Supplier s) throws ValidationException, InventoryException;

    void update(Supplier s) throws ValidationException, InventoryException;

    void delete(int id) throws ValidationException, InventoryException;

    List<Supplier> getAll() throws InventoryException;

    Supplier getById(int id) throws ValidationException, InventoryException;
}
