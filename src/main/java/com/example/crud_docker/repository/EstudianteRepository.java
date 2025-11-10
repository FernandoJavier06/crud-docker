/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crud_docker.repository;

import com.example.crud_docker.model.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EstudianteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class EstudianteRowMapper implements RowMapper<Estudiante> {
        @Override
        public Estudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
            Estudiante estudiante = new Estudiante();
            estudiante.setId(rs.getLong("id"));
            estudiante.setNombre(rs.getString("nombre"));
            estudiante.setApellido(rs.getString("apellido"));
            estudiante.setEmail(rs.getString("email"));
            return estudiante;
        }
    }

    public List<Estudiante> findAll() {
        String sql = "SELECT * FROM estudiantes";
        return jdbcTemplate.query(sql, new EstudianteRowMapper());
    }

    /**
     * CREATE (Crear un nuevo estudiante)
     * Usamos .update() para INSERT, UPDATE o DELETE
     */
    public int save(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre, apellido, email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getEmail());
    }

    /**
     * READ (Leer uno solo por ID)
     */
    public Estudiante findById(Long id) {
        String sql = "SELECT * FROM estudiantes WHERE id = ?";
        try {
            // .queryForObject() espera exactamente 1 resultado
            return jdbcTemplate.queryForObject(sql, new EstudianteRowMapper(), id);
        } catch (Exception e) {
            // Si no lo encuentra, queryForObject lanza una excepción
            return null;
        }
    }

    /**
     * UPDATE (Actualizar un estudiante)
     */
    public int update(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre = ?, apellido = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getEmail(),
                estudiante.getId());
    }

    /**
     * DELETE (Borrar un estudiante)
     */
    public int deleteById(Long id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
