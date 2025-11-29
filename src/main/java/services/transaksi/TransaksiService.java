package services.transaksi;

import interfaces.Subject;
import models.Transaksi;
import java.util.List;

import exception.InventoryException;
import exception.ValidationException;

public interface TransaksiService extends Subject {
    void createMasuk(int barangId, int qty, Integer createdBy, String catatan) throws ValidationException;
    void createKeluar(int barangId, int qty, Integer createdBy, String catatan) throws ValidationException;
    List<Transaksi> getAll() throws InventoryException;
}