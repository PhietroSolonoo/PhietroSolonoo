package br.com.doctorlab;

import br.com.doctorlab.application.service.ConsultaService;
import br.com.doctorlab.application.service.PacienteService;
import br.com.doctorlab.domain.Consulta;
import br.com.doctorlab.domain.Paciente;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;



public class DoctorLabTest {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DOCTOR LAB - TESTES DDD ===");

        PacienteService pacienteService = new PacienteService();
        ConsultaService consultaService = new ConsultaService();

        try {
            // 1. Teste de cadastro de paciente
            System.out.println("\n1. 🧪 Testando cadastro de paciente...");
            Paciente paciente = new Paciente(
                    "João Silva",
                    "joao.silva@email.com",
                    "senha123",
                    LocalDate.of(1980, 5, 15),
                    "12345678901",
                    true,  // deficiencia visual
                    false, // deficiencia auditiva
                    "Precisa de fonte ampliada"
            );

            pacienteService.cadastrarPaciente(paciente);
            System.out.println("✅ Paciente cadastrado: " + paciente.getNome());

            // 2. Teste de login
            System.out.println("\n2. 🔐 Testando login...");
            Paciente pacienteLogado = pacienteService.login("joao.silva@email.com", "senha123");
            if (pacienteLogado != null) {
                System.out.println("✅ Login bem-sucedido: " + pacienteLogado.getNome());
            } else {
                System.out.println("❌ Falha no login");
            }

            // 3. Teste de cálculo de idade
            System.out.println("\n3. 📅 Testando cálculo de idade...");
            int idade = pacienteService.calcularIdade(paciente.getDataNascimento());
            System.out.println("✅ Idade do paciente: " + idade + " anos");

            // 4. Teste de verificação de idoso
            System.out.println("\n4. 👴 Testando verificação de idoso...");
            boolean isIdoso = pacienteService.isIdoso(paciente);
            System.out.println("✅ É idoso: " + isIdoso);

            // 5. Teste de agendamento de consulta
            System.out.println("\n5. 🩺 Testando agendamento de consulta...");
            Consulta consulta = new Consulta(
                    "João Silva",
                    LocalDate.now().plusDays(7),
                    LocalTime.of(10, 30),
                    "Oftalmologia",
                    "Agendada"
            );

            consultaService.agendarConsulta(consulta);
            System.out.println("✅ Consulta agendada: " + consulta.getEspecialidade());

            // 6. Teste de verificação de disponibilidade
            System.out.println("\n6. ⏰ Testando verificação de disponibilidade...");
            boolean disponivel = consultaService.verificarDisponibilidade(
                    LocalDate.now().plusDays(7),
                    LocalTime.of(11, 0)
            );
            System.out.println("✅ Horário disponível: " + disponivel);

            // 7. Teste de listagem
            System.out.println("\n7. 📋 Testando listagem...");
            System.out.println("✅ Pacientes cadastrados: " + pacienteService.listarTodos().size());
            System.out.println("✅ Consultas agendadas: " + consultaService.listarTodas().size());

            // 8. Teste de cancelamento de consulta
            System.out.println("\n8. ❌ Testando cancelamento de consulta...");
            consultaService.cancelarConsulta(consulta.getId());
            System.out.println("✅ Consulta cancelada com sucesso");

        } catch (Exception e) {
            System.out.println("❌ Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== TESTES CONCLUÍDOS ===");
    }
}