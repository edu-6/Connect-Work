package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaUnitariaString;
import com.mycompany.connect.work.api.interfaces.EdicionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.interfaces.BuscarVariosString;
import com.mycompany.connect.work.api.interfaces.CreacionReturnId;
import com.mycompany.connect.work.api.modelos.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriasDB implements CreacionReturnId<Categoria>, EdicionEntidad<Categoria>, BusquedaUnitariaString<Categoria>, ExtraerEntidad<Categoria>, BuscarVariosString<Categoria> {

    private static final String CREAR = "INSERT INTO categoria (nombre, activa) VALUES (?, ?)";
    private static final String EDITAR = "UPDATE categoria SET nombre = ?, activa = ? WHERE id = ?"; 
    
    private static final String BUSCAR_POR_NOMBRE = "SELECT * FROM categoria WHERE nombre = ?";
    
    private static final String BUSCAR_TODOS = "SELECT * FROM categoria";
    private static final String BUSCAR_ACTIVOS = "SELECT * FROM categoria WHERE activa = true";

    public static String getBuscarTodosQuery() {
        return BUSCAR_TODOS;
    }

    public static String getBuscarActivosQuery() {
        return BUSCAR_ACTIVOS;
    }
    
    @Override
    public int crear(Categoria entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(CREAR, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, entidad.getNombre());
            ps.setBoolean(2, entidad.isActiva());
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al crear categoria: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void editar(Categoria entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)) {
            ps.setString(1, entidad.getNombre());
            ps.setBoolean(2, entidad.isActiva());
            ps.setInt(3, entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al editar categoria: " + e.getMessage());
        }
    }

    @Override
    public Categoria buscar(String nombre) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar categoria: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Categoria extraer(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("id"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setActiva(rs.getBoolean("activa"));
        return categoria;
    }

    @Override
    public ArrayList<Categoria> buscarVariosPorString(String query) throws DBException {
        ArrayList<Categoria> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar varias categorias: " + e.getMessage());
        }
        return lista;
    }
}
