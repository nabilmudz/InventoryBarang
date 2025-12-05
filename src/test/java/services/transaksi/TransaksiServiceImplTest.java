package services.transaksi;

import dao.barang.BarangDAO;
import dao.transaksi.TransaksiDAO;
import exception.ValidationException;
import interfaces.Observer;
import models.Barang;
import models.Transaksi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaksiServiceImplTest {

    private TransaksiDAO transaksiDAO;
    private BarangDAO barangDAO;
    private TransaksiServiceImpl transaksiService;

    @BeforeEach
    void setUp() {
        transaksiDAO = mock(TransaksiDAO.class);
        barangDAO = mock(BarangDAO.class);
        transaksiService = new TransaksiServiceImpl(transaksiDAO, barangDAO);
    }

    // ===========================
    //   CREATE MASUK - SUKSES
    // ===========================
    @Test
    void testCreateMasukSuccessUpdatesStokAndNotifyObserver() throws Exception {
        int barangId = 1;
        int qty = 5;
        Integer userId = 10;
        String catatan = "Restock";

        Barang barang = new Barang();
        barang.setId(barangId);
        barang.setStok(10);

        when(barangDAO.getById(barangId)).thenReturn(barang);

        Observer o = mock(Observer.class);
        transaksiService.registerObserver(o);

        Transaksi result = transaksiService.createMasuk(barangId, qty, userId, catatan);

        assertNotNull(result);
        assertEquals(barangId, result.getBarangId());
        assertEquals(qty, result.getQty());
        assertEquals(Transaksi.JENIS_MASUK, result.getJenis());
        assertNotNull(result.getTanggal());

        verify(transaksiDAO, times(1)).insert(any(Transaksi.class));
        verify(barangDAO, times(1)).update(argThat(b -> b.getStok() == 15));
        verify(o, times(1)).update();
    }

    // ===========================
    //   CREATE MASUK - GAGAL (QTY <= 0)
    // ===========================
    @Test
    void testCreateMasukFailQtyKurangSamaDenganNol() throws Exception{
        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> transaksiService.createMasuk(1, 0, 1, "x")
        );
        assertTrue(ex.getMessage().contains("Qty tidak boleh <= 0"));

        verify(transaksiDAO, never()).insert(any());
        verify(barangDAO, never()).update(any());
    }

    // ===========================
    //   CREATE MASUK - GAGAL (BARANG TIDAK ADA)
    // ===========================
    @Test
    void testCreateMasukFailBarangTidakDitemukan() throws Exception {
        int barangId = 99;

        when(barangDAO.getById(barangId)).thenReturn(null);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> transaksiService.createMasuk(barangId, 5, 1, "x")
        );
        assertTrue(ex.getMessage().contains("Barang dengan ID " + barangId + " tidak ditemukan"));

        verify(transaksiDAO, never()).insert(any());
        verify(barangDAO, never()).update(any());
    }

    // ===========================
    //   CREATE KELUAR - SUKSES
    // ===========================
    @Test
    void testCreateKeluarSuccessKurangiStokDanNotifyObserver() throws Exception {
        int barangId = 1;
        int qty = 3;
        Integer userId = 10;
        String catatan = "Penjualan";

        Barang barang = new Barang();
        barang.setId(barangId);
        barang.setStok(10);

        when(barangDAO.getById(barangId)).thenReturn(barang);

        Observer o = mock(Observer.class);
        transaksiService.registerObserver(o);

        Transaksi result = transaksiService.createKeluar(barangId, qty, userId, catatan);

        assertNotNull(result);
        assertEquals(barangId, result.getBarangId());
        assertEquals(qty, result.getQty());
        assertEquals(Transaksi.JENIS_KELUAR, result.getJenis());
        assertNotNull(result.getTanggal());

        verify(transaksiDAO, times(1)).insert(any(Transaksi.class));
        verify(barangDAO, times(1)).update(argThat(b -> b.getStok() == 7));
        verify(o, times(1)).update();
    }

    // ===========================
    //   CREATE KELUAR - GAGAL (STOK KURANG)
    // ===========================
    @Test
    void testCreateKeluarFailStokTidakCukup()  throws Exception{
        int barangId = 1;

        Barang barang = new Barang();
        barang.setId(barangId);
        barang.setStok(2);

        when(barangDAO.getById(barangId)).thenReturn(barang);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> transaksiService.createKeluar(barangId, 5, 1, "x")
        );
        assertTrue(ex.getMessage().contains("Stok tidak cukup"));

        verify(transaksiDAO, never()).insert(any());
        verify(barangDAO, never()).update(any());
    }

    // ===========================
    //   CREATE KELUAR - GAGAL (BARANG TIDAK ADA)
    // ===========================
    @Test
    void testCreateKeluarFailBarangTidakDitemukan() throws Exception {
        int barangId = 99;

        when(barangDAO.getById(barangId)).thenReturn(null);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> transaksiService.createKeluar(barangId, 2, 1, "x")
        );
        assertTrue(ex.getMessage().contains("Barang dengan ID " + barangId + " tidak ditemukan"));

        verify(transaksiDAO, never()).insert(any());
        verify(barangDAO, never()).update(any());
    }

    // ===========================
    //   GET ALL
    // ===========================
    @Test
    void testGetAllReturnsListFromDAO() throws Exception {
        Transaksi t1 = new Transaksi(1, 5, Transaksi.JENIS_MASUK, new java.util.Date(), 1, "a");
        Transaksi t2 = new Transaksi(1, 2, Transaksi.JENIS_KELUAR, new java.util.Date(), 1, "b");

        when(transaksiDAO.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Transaksi> result = transaksiService.getAll();

        assertEquals(2, result.size());
        verify(transaksiDAO, times(1)).findAll();
    }
}
