# Inventory Management Java (Console Application)

Aplikasi manajemen inventaris sederhana berbasis **Java (console)** dengan:

- DAO Pattern  
- Service Layer  
- Facade  
- Observer  
- MySQL

Modul:

- User  
- Supplier  
- Barang  
- Transaksi (MASUK/KELUAR)

---

## 1. Teknologi

- Java 17+  
- Apache Maven  
- MySQL Server  
- MySQL JDBC Driver (via Maven)

---

## 2. Prasyarat

```bash
java -version
mvn -version
```
MySQL berjalan (mis. localhost:3306) dan kredensial database tersedia.

## 3. Setup Database
```sql
CREATE DATABASE inventory_db;
USE inventory_db;
sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE supplier (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    kontak VARCHAR(50),
    alamat VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE barang (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    harga DECIMAL(10,2) NOT NULL,
    supplier_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_barang_supplier
        FOREIGN KEY (supplier_id) REFERENCES supplier(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE transaksi (
    id INT PRIMARY KEY AUTO_INCREMENT,
    barang_id INT NOT NULL,
    qty INT NOT NULL,
    jenis ENUM('MASUK','KELUAR') NOT NULL,
    tanggal DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT,
    catatan VARCHAR(255),
    CONSTRAINT fk_transaksi_barang
        FOREIGN KEY (barang_id) REFERENCES barang(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_transaksi_user
        FOREIGN KEY (created_by) REFERENCES users(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);
```

## 4. Konfigurasi application.properties
src/main/resources/application.properties:
properties
```bash
db.url=jdbc:mysql://localhost:3306/inventory_db
db.username=root
db.password=
```
Sesuaikan nilai di atas dengan environment Anda.

## 5. Build
```bash
mvn clean install
```
## 6. Menjalankan Aplikasi
mvn clean javafx:run
