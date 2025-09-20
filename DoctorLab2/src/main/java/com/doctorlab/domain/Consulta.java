package br.com.doctorlab.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private Long id;
    private String nomePaciente;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private String especialidade;
    private String status;

    public Consulta() {}

    public Consulta(String nomePaciente, LocalDate dataConsulta, LocalTime horaConsulta,
                    String especialidade, String status) {
        this.nomePaciente = nomePaciente;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.especialidade = especialidade;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }
    public LocalDate getDataConsulta() { return dataConsulta; }
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; }
    public LocalTime getHoraConsulta() { return horaConsulta; }
    public void setHoraConsulta(LocalTime horaConsulta) { this.horaConsulta = horaConsulta; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Consulta [id=" + id + ", paciente=" + nomePaciente + ", data=" + dataConsulta +
                ", hora=" + horaConsulta + ", especialidade=" + especialidade + ", status=" + status + "]";
    }
}