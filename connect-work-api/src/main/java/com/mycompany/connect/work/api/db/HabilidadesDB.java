package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BuscarVariosInt;
import com.mycompany.connect.work.api.interfaces.BuscarVariosString;
import com.mycompany.connect.work.api.interfaces.BusquedaUnitariaString;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.EdicionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.Habilidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HabilidadesDB implements CreacionEntidad<Habilidad>, EdicionEntidad<Habilidad>,
        BusquedaUnitariaString<Habilidad>, ExtraerEntidad<Habilidad>, BuscarVariosInt<Habilidad>, BuscarVariosString<Habilidad> {

    private static final String CREAR = "INSERT INTO habilidad (nombre, activa) VALUES (?, ?)";
    private static final String EDITAR = "UPDATE habilidad SET nombre = ?, activa = ? WHERE id = ?";

    private static final String BUSCAR_POR_NOMBRE = "SELECT * FROM habilidad WHERE nombre = ?";

    private static final String BUSCAR_TODOS = "SELECT * FROM habilidad";
    private static final String BUSCAR_ACTIVOS = "SELECT * FROM habilidad WHERE activa = true";

    private static final String BUSCAR_HABILIDADES_EN_CATEGORIA
            = "SELECT h.* FROM habilidad h "
            + "JOIN habilidad_categoria hc ON h.id = hc.id_habilidad "
            + "WHERE hc.id_categoria = ?";

    public static String getBuscarTodosQuery() {
        return BUSCAR_TODOS;
    }

    public static String getBuscarActivosQuery() {
        return BUSCAR_ACTIVOS;
    }

    @Override
    public void crear(Habilidad entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {
            ps.setString(1, entidad.getNombre());
            ps.setBoolean(2, entidad.isActiva());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al crear habilidad: " + e.getMessage());
        }
    }

    @Override
    public void editar(Habilidad entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)) {
            ps.setString(1, entidad.getNombre());
            ps.setBoolean(2, entidad.isActiva());
            ps.setInt(3, entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al editar habilidad: " + e.getMessage());
        }
    }

    @Override
    public Habilidad buscar(String nombre) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar habilidad: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Habilidad extraer(ResultSet rs) throws SQLException {
        Habilidad habilidad = new Habilidad();
        habilidad.setId(rs.getInt("id"));
        habilidad.setNombre(rs.getString("nombre"));
        habilidad.setActiva(rs.getBoolean("activa"));
        return habilidad;
    }

    @Override
    public ArrayList<Habilidad> buscarVariosPorString(String query) throws DBException {
        ArrayList<Habilidad> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar varias habilidades: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public ArrayList<Habilidad> buscarVariosInt(int param) throws DBException {
        ArrayList<Habilidad> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_HABILIDADES_EN_CATEGORIA)) {
            ps.setInt(1, param);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar varias habilidades en categoria: " + e.getMessage());
        }
        return lista;
    }
}
