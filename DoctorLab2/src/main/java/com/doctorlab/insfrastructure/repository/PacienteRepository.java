package br.com.doctorlab.infrastructure.repository;

import br.com.doctorlab.domain.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    // CREATE
    public void salvar(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nome, email, senha, data_nascimento, cpf, " +
                "deficiencia_visual, deficiencia_auditiva, necessidades_especiais) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getSenha());
            stmt.setDate(4, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(5, paciente.getCpf());
            stmt.setBoolean(6, paciente.isDeficienciaVisual());
            stmt.setBoolean(7, paciente.isDeficienciaAuditiva());
            stmt.setString(8, paciente.getNecessidadesEspeciais());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    // READ (by ID)
    public Paciente buscarPorId(Long id) {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        Paciente paciente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = mapearPaciente(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente: " + e.getMessage());
        }

        return paciente;
    }

    // READ (by Email)
    public Paciente buscarPorEmail(String email) {
        String sql = "SELECT * FROM pacientes WHERE email = ?";
        Paciente paciente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = mapearPaciente(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por email: " + e.getMessage());
        }

        return paciente;
    }

    // READ (all)
    public List<Paciente> listarTodos() {
        String sql = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes: " + e.getMessage());
        }

        return pacientes;
    }

    // UPDATE
    public void atualizar(Paciente paciente) {
        String sql = "UPDATE pacientes SET nome = ?, email = ?, senha = ?, data_nascimento = ?, " +
                "cpf = ?, deficiencia_visual = ?, deficiencia_auditiva = ?, " +
                "necessidades_especiais = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getSenha());
            stmt.setDate(4, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(5, paciente.getCpf());
            stmt.setBoolean(6, paciente.isDeficienciaVisual());
            stmt.setBoolean(7, paciente.isDeficienciaAuditiva());
            stmt.setString(8, paciente.getNecessidadesEspeciais());
            stmt.setLong(9, paciente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    // DELETE
    public void deletar(Long id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar paciente: " + e.getMessage());
        }
    }

    // Método auxiliar para mapear ResultSet para Paciente
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setId(rs.getLong("id"));
        paciente.setNome(rs.getString("nome"));
        paciente.setEmail(rs.getString("email"));
        paciente.setSenha(rs.getString("senha"));
        paciente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        paciente.setCpf(rs.getString("cpf"));
        paciente.setDeficienciaVisual(rs.getBoolean("deficiencia_visual"));
        paciente.setDeficienciaAuditiva(rs.getBoolean("deficiencia_auditiva"));
        paciente.setNecessidadesEspeciais(rs.getString("necessidades_especiais"));
        return paciente;
    }
}