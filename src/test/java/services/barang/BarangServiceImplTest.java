package services.barang;

import dao.barang.BarangDAO;
import models.Barang;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BarangServiceImplTest {

    @Mock
    private BarangDAO barangDAO;

    @InjectMocks
    private BarangServiceImpl barangService;

    private Barang testBarang;

    @Before
    public void setUp() {
        testBarang = new Barang();
        testBarang.setId(1);
        testBarang.setNama("Test Laptop");
        testBarang.setStok(10);
        testBarang.setHarga(5000000.0);
        testBarang.setSupplierId(1);
        testBarang.setCreatedAt(LocalDateTime.now());
        testBarang.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreateBarangValid() throws Exception {
        barangService.create(testBarang);
        verify(barangDAO, times(1)).insert(testBarang);
    }

    @Test
    public void testGetAllBarang() throws Exception {
        when(barangDAO.findAll()).thenReturn(Collections.singletonList(testBarang));

        List<Barang> result = barangService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Laptop", result.get(0).getNama());
        verify(barangDAO, times(1)).findAll();
    }

    @Test(expected = Exception.class)
    public void testCreateBarangNamaKosong() throws Exception {
        testBarang.setNama("");
        barangService.create(testBarang);
        verify(barangDAO, never()).insert(any(Barang.class));
    }

    @Test(expected = Exception.class)
    public void testCreateBarangStokMinus() throws Exception {
        testBarang.setStok(-5);
        barangService.create(testBarang);
        verify(barangDAO, never()).insert(any(Barang.class));
    }

    @Test(expected = Exception.class)
    public void testCreateBarangHargaMinus() throws Exception {
        testBarang.setHarga(-1000.0);
        barangService.create(testBarang);
        verify(barangDAO, never()).insert(any(Barang.class));
    }
}