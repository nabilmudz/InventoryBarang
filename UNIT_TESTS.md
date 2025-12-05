# Unit Tests untuk Barang Module

## Test Summary

✅ **BUILD SUCCESS** - All 29 tests passed!

### Test Breakdown

#### 1. **BarangTest** (12 tests) - Model Tests
Tests untuk `Barang.java` model class memastikan getters dan setters berfungsi dengan baik.

- `testBarangConstructor()` - Verifikasi konstruktor
- `testSetAndGetId()` - Test ID field
- `testSetAndGetNama()` - Test Nama field
- `testSetAndGetStok()` - Test Stok field
- `testSetAndGetHarga()` - Test Harga field
- `testSetAndGetSupplierId()` - Test Supplier ID field
- `testSetAndGetCreatedAt()` - Test Created timestamp
- `testSetAndGetUpdatedAt()` - Test Updated timestamp
- `testBarangWithAllFields()` - Test semua fields bersamaan
- `testStokZero()` - Test stok = 0 (valid)
- `testHargaZero()` - Test harga = 0 (valid)
- `testMultipleBarangInstances()` - Test multiple instances tidak saling mempengaruhi

#### 2. **BarangDAOTest** (8 tests) - Data Access Layer Tests
Integration tests untuk `BarangDAOImpl.java` dan database operations.

- `testInsertBarang()` - Insert barang baru
- `testFindByIdBarang()` - Find barang by ID
- `testFindAllBarang()` - Get semua barang
- `testUpdateBarang()` - Update barang existing
- `testDeleteBarang()` - Delete barang
- `testFindByNama()` - Find barang by nama
- `testFindByNamaNotFound()` - Find barang tidak ada
- `testExistInTransaksi()` - Check apakah barang ada di transaksi

#### 3. **BarangServiceImplTest** (9 tests) - Business Logic Tests
Unit tests untuk `BarangServiceImpl.java` validasi business logic.

**Validasi Nama:**
- `testCreateBarangNamaEmpty()` - Nama tidak boleh kosong
- `testCreateBarangNamaNull()` - Nama tidak boleh null

**Validasi Stok:**
- `testCreateBarangStokNegative()` - Stok tidak boleh negatif
- `testUpdateBarangStokNegative()` - Stok update tidak boleh negatif

**Validasi Harga:**
- `testCreateBarangHargaNegative()` - Harga tidak boleh negatif

**Validasi Supplier:**
- `testCreateBarangSupplierIdInvalid()` - Supplier ID harus > 0

**Operasi Get:**
- `testGetAllBarangReturnsNotNull()` - getAll() tidak return null
- `testUpdateBarangNamaEmpty()` - Update dengan nama kosong ditolak
- `testGetByIdBarangLoadsCorrectly()` - getById() bekerja dengan benar

## Running Tests

```bash
# Run semua tests
mvn test

# Run hanya service tests
mvn test -Dtest=BarangServiceImplTest

# Run hanya model tests
mvn test -Dtest=BarangTest

# Run hanya DAO tests
mvn test -Dtest=BarangDAOTest

# Run dengan coverage
mvn test jacoco:report
```

## Test Coverage

| Module | Classes | Tests | Status |
|--------|---------|-------|--------|
| Models | 1 | 12 | ✅ PASS |
| DAO | 1 | 8 | ✅ PASS |
| Service | 1 | 9 | ✅ PASS |
| **Total** | **3** | **29** | **✅ PASS** |

## Teknologi yang Digunakan

- **JUnit 4.13.2** - Unit testing framework
- **Mockito 4.8.1** - Mocking framework
- **Maven Surefire 3.0.0** - Test runner

## Dependencies di pom.xml

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>4.8.1</version>
    <scope>test</scope>
</dependency>
```

## Test Scenarios Tercakup

### Create (Create) Validation:
✅ Nama validation (empty, null)
✅ Stok validation (negative)
✅ Harga validation (negative)
✅ Supplier ID validation (must be > 0)
✅ Duplicate check

### Update Validation:
✅ Nama validation
✅ Stok validation
✅ Harga validation

### Read Operations:
✅ Get all barang
✅ Get barang by ID
✅ Find barang by nama
✅ Check barang in transaksi

### Delete Operations:
✅ Delete barang (integration test dengan DAO)

### Model Tests:
✅ All fields setter/getter
✅ Constructor
✅ Multiple instances

---

**Last Updated:** December 5, 2025
**Status:** ✅ All tests passing
