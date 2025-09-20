package br.com.doctorlab.application.service;

import br.com.doctorlab.domain.Paciente;
import br.com.doctorlab.infrastructure.repository.PacienteRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class PacienteService {
    private PacienteRepository pacienteRepository;

    public PacienteService() {
        this.pacienteRepository = new PacienteRepository();
    }

    // MÉTODO 1: Cadastrar paciente com validações
    public void cadastrarPaciente(Paciente paciente) {
        validarPaciente(paciente);
        pacienteRepository.salvar(paciente);
    }

    // MÉTODO 2: Validar dados do paciente (regra de negócio)
    private void validarPaciente(Paciente paciente) {
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do paciente é obrigatório");
        }

        if (paciente.getEmail() == null || !paciente.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (paciente.getSenha() == null || paciente.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }

        if (paciente.getDataNascimento() == null || calcularIdade(paciente.getDataNascimento()) < 0) {
            throw new IllegalArgumentException("Data de nascimento inválida");
        }

        if (!validarCPF(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Verificar se email já existe
        if (pacienteRepository.buscarPorEmail(paciente.getEmail()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }

    // MÉTODO 3: Validar CPF (regra de negócio complexa)
    private boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        // Verificar se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            // Cálculo do primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) firstDigit = 0;

            // Cálculo do segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;

            return digits[9] == firstDigit && digits[10] == secondDigit;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // MÉTODO 4: Calcular idade
    public int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // MÉTODO 5: Verificar se paciente é idoso
    public boolean isIdoso(Paciente paciente) {
        return calcularIdade(paciente.getDataNascimento()) >= 60;
    }

    // MÉTODO 6: Buscar pacientes com necessidades especiais
    public List<Paciente> buscarPacientesComNecessidadesEspeciais() {
        List<Paciente> todosPacientes = pacienteRepository.listarTodos();
        return todosPacientes.stream()
                .filter(p -> p.isDeficienciaVisual() || p.isDeficienciaAuditiva() ||
                        (p.getNecessidadesEspeciais() != null && !p.getNecessidadesEspeciais().isEmpty()))
                .toList();
    }

    // MÉTODO 7: Login do paciente
    public Paciente login(String email, String senha) {
        Paciente paciente = pacienteRepository.buscarPorEmail(email);
        if (paciente != null && paciente.getSenha().equals(senha)) {
            return paciente;
        }
        return null;
    }

    // CRUD methods
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.buscarPorId(id);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.listarTodos();
    }

    public void atualizarPaciente(Paciente paciente) {
        validarPaciente(paciente);
        pacienteRepository.atualizar(paciente);
    }

    public void deletarPaciente(Long id) {
        pacienteRepository.deletar(id);
    }
}