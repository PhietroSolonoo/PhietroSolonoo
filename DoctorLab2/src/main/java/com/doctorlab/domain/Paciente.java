package br.com.doctorlab.domain;

import java.time.LocalDate;

public class Paciente {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String cpf;
    private boolean deficienciaVisual;
    private boolean deficienciaAuditiva;
    private String necessidadesEspeciais;

    public Paciente() {}

    public Paciente(String nome, String email, String senha, LocalDate dataNascimento,
                    String cpf, boolean deficienciaVisual, boolean deficienciaAuditiva,
                    String necessidadesEspeciais) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.deficienciaVisual = deficienciaVisual;
        this.deficienciaAuditiva = deficienciaAuditiva;
        this.necessidadesEspeciais = necessidadesEspeciais;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public boolean isDeficienciaVisual() { return deficienciaVisual; }
    public void setDeficienciaVisual(boolean deficienciaVisual) { this.deficienciaVisual = deficienciaVisual; }
    public boolean isDeficienciaAuditiva() { return deficienciaAuditiva; }
    public void setDeficienciaAuditiva(boolean deficienciaAuditiva) { this.deficienciaAuditiva = deficienciaAuditiva; }
    public String getNecessidadesEspeciais() { return necessidadesEspeciais; }
    public void setNecessidadesEspeciais(String necessidadesEspeciais) { this.necessidadesEspeciais = necessidadesEspeciais; }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + "]";
    }
}