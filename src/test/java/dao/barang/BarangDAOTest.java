package dao.barang;

import org.junit.Before;
import org.junit.Test;
import models.Barang;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Integration test untuk BarangDAO
 * Test ini memerlukan database yang sudah setup
 */
public class BarangDAOTest {

    private BarangDAO barangDAO;
    private Barang testBarang;

    @Before
    public void setUp() {
        // gunakan implementasi DAO nyata untuk integration test
        barangDAO = new BarangDAOImpl();

        testBarang = new Barang();
        testBarang.setNama("Test Barang");
        testBarang.setStok(100);
        testBarang.setHarga(50000.0);
        testBarang.setSupplierId(1);
        testBarang.setCreatedAt(LocalDateTime.now());
        testBarang.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    public void testInsertBarang() throws Exception {
        // Arrange - Barang sudah disiapkan di setUp

        // Act
        barangDAO.insert(testBarang);

        // Assert
        assertNotNull(testBarang.getId());
        assertTrue(testBarang.getId() > 0);
    }

    @Test
    public void testFindByIdBarang() throws Exception {
        // Arrange
        barangDAO.insert(testBarang);
        int barangId = testBarang.getId();

        // Act
        Barang result = barangDAO.findById(barangId);

        // Assert
        assertNotNull(result);
        assertEquals(barangId, result.getId());
        assertEquals("Test Barang", result.getNama());
        assertEquals(100, result.getStok());
    }

    @Test
    public void testFindAllBarang() throws Exception {
        // Act
        List<Barang> result = barangDAO.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    public void testUpdateBarang() throws Exception {
        // Arrange
        barangDAO.insert(testBarang);
        int barangId = testBarang.getId();
        
        testBarang.setNama("Updated Barang");
        testBarang.setStok(50);

        // Act
        barangDAO.update(testBarang);

        // Assert
        Barang updated = barangDAO.findById(barangId);
        assertNotNull(updated);
        assertEquals("Updated Barang", updated.getNama());
        assertEquals(50, updated.getStok());
    }

    @Test
    public void testDeleteBarang() throws Exception {
        // Arrange
        barangDAO.insert(testBarang);
        int barangId = testBarang.getId();

        // Act
        barangDAO.delete(barangId);

        // Assert
        Barang deleted = barangDAO.findById(barangId);
        assertNull(deleted);
    }

    @Test
    public void testFindByNama() throws Exception {
        // Arrange
        barangDAO.insert(testBarang);

        // Act
        Barang result = barangDAO.findByNama("Test Barang");

        // Assert
        assertNotNull(result);
        assertEquals("Test Barang", result.getNama());
    }

    @Test
    public void testFindByNamaNotFound() throws Exception {
        // Act
        Barang result = barangDAO.findByNama("NonExistentBarang");

        // Assert
        assertNull(result);
    }

    @Test
    public void testExistInTransaksi() throws Exception {
        // Arrange
        barangDAO.insert(testBarang);

        // Act
        boolean exists = barangDAO.existInTransaksi(testBarang.getId());

        // Assert - depends on transaction data
        assertFalse(exists); // Asumsi barang baru tidak ada transaksi
    }

}
