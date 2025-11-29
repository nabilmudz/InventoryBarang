package dao.barang;

import model.Barang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAOImpl implements BarangDAO {

    @Override
    public boolean insert(Barang barang) {
        String sql = "INSERT INTO barang (nama, stok, harga, supplier_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, barang.getNama());
            ps.setInt(2, barang.getStok());
            ps.setDouble(3, barang.getHarga());
            ps.setInt(4, barang.getSupplierId());
            ps.setTimestamp(5, Timestamp.valueOf(barang.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(barang.getUpdatedAt()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Barang barang) {
        String sql = "UPDATE barang SET nama=?, stok=?, harga=?, supplier_id=?, updated_at=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, barang.getNama());
            ps.setInt(2, barang.getStok());
            ps.setDouble(3, barang.getHarga());
            ps.setInt(4, barang.getSupplierId());
            ps.setTimestamp(5, Timestamp.valueOf(barang.getUpdatedAt()));
            ps.setInt(6, barang.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM barang WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Barang getById(int id) {
        String sql = "SELECT * FROM barang WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToBarang(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapToBarang(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Barang mapToBarang(ResultSet rs) throws SQLException {
        Barang b = new Barang();

        b.setId(rs.getInt("id"));
        b.setNama(rs.getString("nama"));
        b.setStok(rs.getInt("stok"));
        b.setHarga(rs.getDouble("harga"));
        b.setSupplierId(rs.getInt("supplier_id"));

        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");

        b.setCreatedAt(created != null ? created.toLocalDateTime() : null);
        b.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);

        return b;
    }
}
