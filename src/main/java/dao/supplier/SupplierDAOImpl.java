package dao.supplier;

import dao.BaseDAO;
import models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl extends BaseDAO<Supplier> implements SupplierDAO {

    private static final String TABLE = "supplier";

    private static final String COL_ID = "id";
    private static final String COL_NAMA = "nama";
    private static final String COL_KONTAK = "kontak";
    private static final String COL_ALAMAT = "alamat";
    private static final String COL_CREATED = "created_at";
    private static final String COL_UPDATED = "updated_at";

    private static final String SQL_SELECT = "SELECT * FROM ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_ID_PARAM = "id = ?";

    @Override
    public void insert(Supplier s) throws Exception {
        String sql = "INSERT INTO " + TABLE + " (" + COL_NAMA + ", " + COL_KONTAK + ", " + COL_ALAMAT + ") VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, s.getNama());
        ps.setString(2, s.getKontak());
        ps.setString(3, s.getAlamat());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            s.setId(rs.getInt(1));
        }
    }

    @Override
    public List<Supplier> findAll() throws Exception {
        List<Supplier> list = new ArrayList<>();
        String sql = SQL_SELECT + TABLE;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Supplier s = new Supplier();
            s.setId(rs.getInt(COL_ID));
            s.setNama(rs.getString(COL_NAMA));
            s.setKontak(rs.getString(COL_KONTAK));
            s.setAlamat(rs.getString(COL_ALAMAT));
            s.setCreatedAt(rs.getTimestamp(COL_CREATED));
            s.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
            list.add(s);
        }
        return list;
    }

    @Override
    public void update(Supplier s) throws Exception {
        String sql = "UPDATE " + TABLE +
                " SET " + COL_NAMA + " = ?, " + COL_KONTAK + " = ?, " + COL_ALAMAT + " = ?" +
                SQL_WHERE + SQL_ID_PARAM;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, s.getNama());
        ps.setString(2, s.getKontak());
        ps.setString(3, s.getAlamat());
        ps.setInt(4, s.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM " + TABLE + SQL_WHERE + SQL_ID_PARAM;
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Supplier findById(int id) throws Exception {
        String sql = SQL_SELECT + TABLE + SQL_WHERE + SQL_ID_PARAM;
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Supplier s = new Supplier();
            s.setId(rs.getInt(COL_ID));
            s.setNama(rs.getString(COL_NAMA));
            s.setKontak(rs.getString(COL_KONTAK));
            s.setAlamat(rs.getString(COL_ALAMAT));
            s.setCreatedAt(rs.getTimestamp(COL_CREATED));
            s.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
            return s;
        }
        return null;
    }

    @Override
    public Supplier findByKontak(String nama, String kontak) throws Exception {
        String sql = SQL_SELECT + TABLE + SQL_WHERE + COL_NAMA + " = ? AND " + COL_KONTAK + " = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nama);
        ps.setString(2, kontak);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Supplier s = new Supplier();
            s.setId(rs.getInt(COL_ID));
            s.setNama(rs.getString(COL_NAMA));
            s.setKontak(rs.getString(COL_KONTAK));
            s.setAlamat(rs.getString(COL_ALAMAT));
            s.setCreatedAt(rs.getTimestamp(COL_CREATED));
            s.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
            return s;
        }
        return null;
    }

    @Override
    public boolean existInBarang(int supplierId) throws Exception {
        String sql = "SELECT COUNT(*) FROM barang" + SQL_WHERE + "supplier_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, supplierId);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    @Override
    public boolean existInTransaksi(int supplierId) throws Exception {
        String sql = """
                SELECT COUNT(*)
                FROM transaksi t
                JOIN barang b ON t.barang_id = b.id
                WHERE b.supplier_id = ?
                """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, supplierId);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }
}
