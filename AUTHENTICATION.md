# Implementasi Authentication

## Deskripsi
Sistem authentication telah diimplementasikan untuk melindungi akses ke aplikasi. User harus login sebelum dapat mengakses halaman utama.

## Komponen yang Ditambahkan

### 1. Model
- **User.java** (`src/main/java/models/User.java`)
  - Menyimpan data user (id, username, password, fullName, role, active)

### 2. Data Access Layer (DAO)
- **UserDAO.java** (`src/main/java/dao/user/UserDAO.java`)
  - Interface untuk operasi user di database
  - Method tambahan: `findByUsername()`, `authenticate()`
  
- **UserDAOImpl.java** (`src/main/java/dao/user/UserDAOImpl.java`)
  - Implementasi CRUD untuk user
  - Database: tabel `users`

### 3. Service Layer
- **UserService.java** (`src/main/java/services/user/UserService.java`)
  - Interface service user

- **UserServiceImpl.java** (`src/main/java/services/user/UserServiceImpl.java`)
  - Logika bisnis authentication
  - Validasi input user

### 4. UI (JavaFX)
- **LoginView.fxml** (`src/main/resources/ui/fx/login/LoginView.fxml`)
  - UI login dengan form username dan password
  
- **LoginController.java** (`src/main/java/ui/fx/login/LoginController.java`)
  - Handle login action
  - Validasi input
  - Navigasi ke main app setelah login berhasil

### 5. Modifikasi Existing Files
- **MainApp.java** - Sekarang menampilkan login page terlebih dahulu
- **MainController.java** - Ditambah method `setCurrentUser()` untuk menyimpan user yang login

## Database Schema

Jalankan SQL script berikut untuk membuat tabel users:

```sql
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

-- Default users
INSERT INTO users (username, password, full_name, role) VALUES
('admin', 'admin123', 'Administrator', 'admin'),
('user', 'user123', 'User Biasa', 'user');
```

Lihat file `database_schema.sql` untuk script lengkap.

## Cara Menggunakan

1. **Setup Database**
   - Jalankan SQL script untuk membuat tabel users
   - Default login credentials:
     - Username: `admin`, Password: `admin123`
     - Username: `user`, Password: `user123`

2. **Menjalankan Aplikasi**
   - Login page akan muncul terlebih dahulu
   - Masukkan username dan password
   - Setelah login berhasil, akan diarahkan ke main page
   - User info akan disimpan di MainController

3. **Mengakses User Current di Controller**
   ```java
   MainController controller = loader.getController();
   User currentUser = controller.getCurrentUser();
   ```

## Catatan Keamanan

- Password disimpan dalam plaintext (untuk development saja)
- Untuk production, gunakan password hashing (bcrypt, scrypt, etc)
- Implementasi session management jika diperlukan
- Tambahkan role-based access control (RBAC) jika diperlukan

## File Structure

```
src/main/java/
├── dao/user/
│   ├── UserDAO.java
│   └── UserDAOImpl.java
├── models/
│   └── User.java
├── services/user/
│   ├── UserService.java
│   └── UserServiceImpl.java
└── ui/fx/
    ├── login/
    │   └── LoginController.java
    └── main/
        ├── MainApp.java (modified)
        └── MainController.java (modified)

src/main/resources/
└── ui/fx/
    └── login/
        └── LoginView.fxml
```
