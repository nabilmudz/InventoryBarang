package services.transaksi;

import interfaces.Subject;
import models.Transaksi;
import java.util.List;

import exception.InventoryException;
import exception.ValidationException;

public interface TransaksiService extends Subject {
    Transaksi createMasuk(int barangId, int qty, Integer createdBy, String catatan) throws ValidationException;
    Transaksi createKeluar(int barangId, int qty, Integer createdBy, String catatan) throws ValidationException;
    List<Transaksi> getAll() throws InventoryException;
}
