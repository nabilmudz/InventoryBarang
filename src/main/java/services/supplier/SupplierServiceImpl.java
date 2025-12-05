package services.supplier;

import dao.supplier.SupplierDAO;
import interfaces.Observer;
import models.Supplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO supplierDAO;
    private final List<Observer> observers = new ArrayList<>();

    public SupplierServiceImpl(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    @Override
    public void create(Supplier s) throws Exception {
        supplierDAO.insert(s);
        notifyObservers();
    }

    @Override
    public void update(Supplier s) throws Exception {
        supplierDAO.update(s);
        notifyObservers();
    }

    @Override
    public void delete(int id) throws Exception {
        supplierDAO.delete(id);
        notifyObservers();
    }

    @Override
    public Supplier findById(int id) throws Exception {
        return supplierDAO.findById(id);
    }

    @Override
    public List<Supplier> findAll() throws Exception {
        return supplierDAO.findAll();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
