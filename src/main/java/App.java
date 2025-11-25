
import java.util.Date;

import facade.InventoryFacade;
import models.Transaksi;

public class App {
    public static void main(String[] args) {
        try {
            InventoryFacade facade = new InventoryFacade();

            Transaksi t = new Transaksi(
                    1,              // barang_id
                    5,              // qty
                    "MASUK",        // jenis
                    new Date(),     // tanggal
                    1,              // created_by
                    "testing insert hardcoded"
            );

            facade.createTransaksi(t);
            System.out.println("Transaksi berhasil dimasukkan!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
