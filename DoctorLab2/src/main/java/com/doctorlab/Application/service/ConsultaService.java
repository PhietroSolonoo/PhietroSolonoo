package br.com.doctorlab.application.service;

import br.com.doctorlab.domain.Consulta;
import br.com.doctorlab.infrastructure.repository.ConsultaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultaService {
    private ConsultaRepository consultaRepository;

    public ConsultaService() {
        this.consultaRepository = new ConsultaRepository();
    }

    // MÉTODO 1: Agendar consulta com validações
    public void agendarConsulta(Consulta consulta) {
        validarConsulta(consulta);
        consultaRepository.salvar(consulta);
    }

    // MÉTODO 2: Validar dados da consulta (regra de negócio)
    private void validarConsulta(Consulta consulta) {
        if (consulta.getNomePaciente() == null || consulta.getNomePaciente().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do paciente é obrigatório");
        }

        if (consulta.getDataConsulta() == null || consulta.getDataConsulta().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data da consulta deve ser futura");
        }

        if (consulta.getHoraConsulta() == null) {
            throw new IllegalArgumentException("Hora da consulta é obrigatória");
        }

        if (consulta.getEspecialidade() == null || consulta.getEspecialidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade é obrigatória");
        }

        // Validar horário comercial (8h às 18h)
        LocalTime hora = consulta.getHoraConsulta();
        if (hora.isBefore(LocalTime.of(8, 0)) || hora.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("Consultas devem ser agendadas entre 8h e 18h");
        }
    }

    // MÉTODO 3: Cancelar consulta
    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.buscarPorId(id);
        if (consulta != null) {
            consulta.setStatus("Cancelada");
            consultaRepository.atualizar(consulta);
        } else {
            throw new IllegalArgumentException("Consulta não encontrada");
        }
    }

    // MÉTODO 4: Verificar disponibilidade de horário
    public boolean verificarDisponibilidade(LocalDate data, LocalTime hora) {
        List<Consulta> consultasDoDia = consultaRepository.buscarPorStatus("Agendada").stream()
                .filter(c -> c.getDataConsulta().equals(data))
                .toList();

        // Verificar se há consulta no mesmo horário
        return consultasDoDia.stream()
                .noneMatch(c -> c.getHoraConsulta().equals(hora));
    }

    // CRUD methods
    public Consulta buscarPorId(Long id) {
        return consultaRepository.buscarPorId(id);
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.listarTodas();
    }

    public List<Consulta> buscarPorStatus(String status) {
        return consultaRepository.buscarPorStatus(status);
    }

    public void atualizarConsulta(Consulta consulta) {
        validarConsulta(consulta);
        consultaRepository.atualizar(consulta);
    }

    public void deletarConsulta(Long id) {
        consultaRepository.deletar(id);
    }
}