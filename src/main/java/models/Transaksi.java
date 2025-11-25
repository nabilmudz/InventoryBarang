package models;

import java.util.Date;

public class Transaksi {
    private int id;
    private int barangId;
    private int qty;
    private String jenis;
    private Date tanggal;
    private Integer createdBy;
    private String catatan;

    public Transaksi() {}

    public Transaksi(int barangId, int qty, String jenis, Date tanggal, Integer createdBy, String catatan) {
        this.barangId = barangId;
        this.qty = qty;
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.createdBy = createdBy;
        this.catatan = catatan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBarangId() { return barangId; }
    public void setBarangId(int barangId) { this.barangId = barangId; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}