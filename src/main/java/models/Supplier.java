package models;

import java.util.Date;

public class Supplier {
    private int id;
    private String nama;
    private String kontak;
    private String alamat;
    private Date createdAt;
    private Date updatedAt;

    public Supplier() {}

    public Supplier(String nama, String kontak, String alamat) {
        this.nama = nama;
        this.kontak = kontak;
        this.alamat = alamat;
    }

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getNama() { 
        return nama; 
    }
    public void setNama(String nama) { 
        this.nama = nama; 
    }

    public String getKontak() { 
        return kontak; 
    }
    public void setKontak(String kontak) { 
        this.kontak = kontak; 
    }

    public String getAlamat() { 
        return alamat; 
    }
    public void setAlamat(String alamat) { 
        this.alamat = alamat; 
    }

    public Date getCreatedAt() { 
        return createdAt; 
    }
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt; 
    }

    public Date getUpdatedAt() { 
        return updatedAt; 
    }
    public void setUpdatedAt(Date updatedAt) { 
        this.updatedAt = updatedAt; 
    }
}