package services.supplier;

import dao.supplier.SupplierDAO;
import interfaces.Observer;
import models.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SupplierServiceImplTest {

    private SupplierDAO supplierDAO;
    private SupplierServiceImpl supplierService;

    @BeforeEach
    void setUp() {
        supplierDAO = mock(SupplierDAO.class);
        supplierService = new SupplierServiceImpl(supplierDAO);
    }

    // ===========================
    //       CREATE
    // ===========================
    @Test
    void testCreateCallsDAOAndNotifyObservers() throws Exception {
        Supplier s = new Supplier("PT Maju", "08111", "Bandung");

        // mock Observer
        Observer o = mock(Observer.class);
        supplierService.registerObserver(o);

        supplierService.create(s);

        verify(supplierDAO, times(1)).insert(s);
        verify(o, times(1)).update();
    }

    // ===========================
    //       UPDATE
    // ===========================
    @Test
    void testUpdateCallsDAOAndNotifyObservers() throws Exception {
        Supplier s = new Supplier("PT Jaya", "08222", "Jakarta");

        Observer o = mock(Observer.class);
        supplierService.registerObserver(o);

        supplierService.update(s);

        verify(supplierDAO, times(1)).update(s);
        verify(o, times(1)).update();
    }

    // ===========================
    //       DELETE
    // ===========================
    @Test
    void testDeleteCallsDAOAndNotifyObservers() throws Exception {

        Observer o = mock(Observer.class);
        supplierService.registerObserver(o);

        supplierService.delete(5);

        verify(supplierDAO, times(1)).delete(5);
        verify(o, times(1)).update();
    }

    // ===========================
    //       FIND BY ID
    // ===========================
    @Test
    void testFindByIdReturnsSupplier() throws Exception {
        Supplier s = new Supplier("A", "B", "C");
        s.setId(10);

        when(supplierDAO.findById(10)).thenReturn(s);

        Supplier result = supplierService.findById(10);

        assertEquals(10, result.getId());
        assertEquals("A", result.getNama());
        verify(supplierDAO, times(1)).findById(10);
    }

    // ===========================
    //       FIND ALL
    // ===========================
    @Test
    void testFindAllReturnsList() throws Exception {
        List<Supplier> data = Arrays.asList(
                new Supplier("X", "123", "Bandung"),
                new Supplier("Y", "456", "Jakarta")
        );

        when(supplierDAO.findAll()).thenReturn(data);

        List<Supplier> result = supplierService.findAll();

        assertEquals(2, result.size());
        verify(supplierDAO, times(1)).findAll();
    }

    // ===========================
    //   OBSERVER REGISTER/REMOVE
    // ===========================
    @Test
    void testObserverRegisterAndRemove() {
        Observer o = mock(Observer.class);

        supplierService.registerObserver(o);
        supplierService.removeObserver(o);

        // panggil notify -> tidak boleh update observer karena sudah dihapus
        supplierService.notifyObservers();

        verify(o, never()).update();
    }
}