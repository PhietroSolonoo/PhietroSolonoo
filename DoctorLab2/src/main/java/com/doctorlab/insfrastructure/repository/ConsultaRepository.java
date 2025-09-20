package br.com.doctorlab.infrastructure.repository;

import br.com.doctorlab.domain.Consulta;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    // CREATE
    public void salvar(Consulta consulta) {
        String sql = "INSERT INTO consultas (nome_paciente, data_consulta, hora_consulta, " +
                "especialidade, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, consulta.getNomePaciente());
            stmt.setDate(2, Date.valueOf(consulta.getDataConsulta()));
            stmt.setTime(3, Time.valueOf(consulta.getHoraConsulta()));
            stmt.setString(4, consulta.getEspecialidade());
            stmt.setString(5, consulta.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    consulta.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage());
        }
    }

    // READ (by ID)
    public Consulta buscarPorId(Long id) {
        String sql = "SELECT * FROM consultas WHERE id = ?";
        Consulta consulta = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    consulta = mapearConsulta(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage());
        }

        return consulta;
    }

    // READ (all)
    public List<Consulta> listarTodas() {
        String sql = "SELECT * FROM consultas";
        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                consultas.add(mapearConsulta(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage());
        }

        return consultas;
    }

    // READ (by status)
    public List<Consulta> buscarPorStatus(String status) {
        String sql = "SELECT * FROM consultas WHERE status = ?";
        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapearConsulta(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas por status: " + e.getMessage());
        }

        return consultas;
    }

    // UPDATE
    public void atualizar(Consulta consulta) {
        String sql = "UPDATE consultas SET nome_paciente = ?, data_consulta = ?, " +
                "hora_consulta = ?, especialidade = ?, status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, consulta.getNomePaciente());
            stmt.setDate(2, Date.valueOf(consulta.getDataConsulta()));
            stmt.setTime(3, Time.valueOf(consulta.getHoraConsulta()));
            stmt.setString(4, consulta.getEspecialidade());
            stmt.setString(5, consulta.getStatus());
            stmt.setLong(6, consulta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    // DELETE
    public void deletar(Long id) {
        String sql = "DELETE FROM consultas WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta: " + e.getMessage());
        }
    }

    // Método auxiliar para mapear ResultSet para Consulta
    private Consulta mapearConsulta(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getLong("id"));
        consulta.setNomePaciente(rs.getString("nome_paciente"));
        consulta.setDataConsulta(rs.getDate("data_consulta").toLocalDate());
        consulta.setHoraConsulta(rs.getTime("hora_consulta").toLocalTime());
        consulta.setEspecialidade(rs.getString("especialidade"));
        consulta.setStatus(rs.getString("status"));
        return consulta;
    }
}