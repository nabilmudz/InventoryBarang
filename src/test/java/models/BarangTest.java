package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BarangTest {

    private Barang barang;

    @Before
    public void setUp() {
        barang = new Barang();
    }

    @Test
    public void testBarangConstructor() {
        // Arrange & Act
        Barang newBarang = new Barang();

        // Assert
        assertNotNull(newBarang);
    }

    @Test
    public void testSetAndGetId() {
        // Arrange & Act
        barang.setId(1);

        // Assert
        assertEquals(1, barang.getId());
    }

    @Test
    public void testSetAndGetNama() {
        // Arrange & Act
        barang.setNama("Laptop");

        // Assert
        assertEquals("Laptop", barang.getNama());
    }

    @Test
    public void testSetAndGetStok() {
        // Arrange & Act
        barang.setStok(10);

        // Assert
        assertEquals(10, barang.getStok());
    }

    @Test
    public void testSetAndGetHarga() {
        // Arrange & Act
        barang.setHarga(5000000.0);

        // Assert
        assertEquals(5000000.0, barang.getHarga(), 0.01);
    }

    @Test
    public void testSetAndGetSupplierId() {
        // Arrange & Act
        barang.setSupplierId(1);

        // Assert
        assertEquals(1, barang.getSupplierId());
    }

    @Test
    public void testSetAndGetCreatedAt() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        barang.setCreatedAt(now);

        // Assert
        assertEquals(now, barang.getCreatedAt());
    }

    @Test
    public void testSetAndGetUpdatedAt() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        barang.setUpdatedAt(now);

        // Assert
        assertEquals(now, barang.getUpdatedAt());
    }

    @Test
    public void testBarangWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        barang.setId(1);
        barang.setNama("Monitor");
        barang.setStok(5);
        barang.setHarga(2000000.0);
        barang.setSupplierId(2);
        barang.setCreatedAt(now);
        barang.setUpdatedAt(now);

        // Assert
        assertEquals(1, barang.getId());
        assertEquals("Monitor", barang.getNama());
        assertEquals(5, barang.getStok());
        assertEquals(2000000.0, barang.getHarga(), 0.01);
        assertEquals(2, barang.getSupplierId());
        assertEquals(now, barang.getCreatedAt());
        assertEquals(now, barang.getUpdatedAt());
    }

    @Test
    public void testStokZero() {
        // Arrange & Act
        barang.setStok(0);

        // Assert
        assertEquals(0, barang.getStok());
    }

    @Test
    public void testHargaZero() {
        // Arrange & Act
        barang.setHarga(0.0);

        // Assert
        assertEquals(0.0, barang.getHarga(), 0.01);
    }

    @Test
    public void testMultipleBarangInstances() {
        // Arrange
        Barang barang1 = new Barang();
        Barang barang2 = new Barang();

        // Act
        barang1.setId(1);
        barang1.setNama("Laptop");
        barang2.setId(2);
        barang2.setNama("Monitor");

        // Assert
        assertNotEquals(barang1.getId(), barang2.getId());
        assertNotEquals(barang1.getNama(), barang2.getNama());
    }

}
