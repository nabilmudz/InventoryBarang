package app;

import facade.InventoryFacade;

public class App {
    public static void main(String[] args) {
        try {
            InventoryFacade facade = new InventoryFacade();

            facade.addTransaksiMasuk(1, 5, 1, "testing transaksi masuk hardcoded");
            System.out.println("Transaksi MASUK berhasil!");

            facade.addTransaksiKeluar(1, 2, 1, "testing transaksi keluar");
            System.out.println("Transaksi KELUAR berhasil!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
