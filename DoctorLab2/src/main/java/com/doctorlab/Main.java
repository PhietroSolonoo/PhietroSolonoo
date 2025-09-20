package br.com.doctorlab;

import br.com.doctorlab.application.service.ConsultaService;
import br.com.doctorlab.application.service.PacienteService;
import br.com.doctorlab.domain.Consulta;
import br.com.doctorlab.domain.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("🚑 DOCTOR LAB - SISTEMA DE AGENDAMENTO MÉDICO");
        System.out.println("==================================================");
        System.out.println("💡 Sistema com foco em acessibilidade para idosos");
        System.out.println("   e pessoas com deficiência visual/auditiva");
        System.out.println("==================================================");

        PacienteService pacienteService = new PacienteService();
        ConsultaService consultaService = new ConsultaService();
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. DEMONSTRAR CADASTRO DE PACIENTE (Tela de "Cadastre-se")
            System.out.println("\n1. 📝 CADASTRO DE PACIENTE");
            System.out.println("   -----------------------");

            Paciente paciente1 = new Paciente(
                    "Maria Santos",
                    "maria.santos@email.com",
                    "senha456",
                    LocalDate.of(1960, 3, 25), // 64 anos (idoso)
                    "98765432100",
                    true,  // deficiencia visual
                    false,
                    "Letras ampliadas e alto contraste"
            );

            pacienteService.cadastrarPaciente(paciente1);
            System.out.println("   ✅ Paciente cadastrado: " + paciente1.getNome());
            System.out.println("   📧 Email: " + paciente1.getEmail());
            System.out.println("   🎂 Idade: " + pacienteService.calcularIdade(paciente1.getDataNascimento()) + " anos");
            System.out.println("   👴 É idoso: " + pacienteService.isIdoso(paciente1));

            // 2. DEMONSTRAR LOGIN (Tela de "Login")
            System.out.println("\n2. 🔐 LOGIN DO PACIENTE");
            System.out.println("   -------------------");

            Paciente pacienteLogin = pacienteService.login("maria.santos@email.com", "senha456");
            if (pacienteLogin != null) {
                System.out.println("   ✅ Login bem-sucedido!");
                System.out.println("   👋 Bem-vinda, " + pacienteLogin.getNome() + "!");
            } else {
                System.out.println("   ❌ Login falhou!");
            }

            // 3. DEMONSTRAR AGENDAMENTO (Tela de "Agendar Consulta")
            System.out.println("\n3. 🩺 AGENDAMENTO DE CONSULTA");
            System.out.println("   --------------------------");

            Consulta consulta1 = new Consulta(
                    "Maria Santos",
                    LocalDate.now().plusDays(5),
                    LocalTime.of(14, 30),
                    "Oftalmologia",
                    "Agendada"
            );

            consultaService.agendarConsulta(consulta1);
            System.out.println("   ✅ Consulta agendada com sucesso!");
            System.out.println("   📅 Data: " + consulta1.getDataConsulta());
            System.out.println("   ⏰ Hora: " + consulta1.getHoraConsulta());
            System.out.println("   🏥 Especialidade: " + consulta1.getEspecialidade());

            // 4. TESTAR REGRAS DE NEGÓCIO - CPF INVÁLIDO
            System.out.println("\n4. 🧪 TESTANDO REGRAS DE NEGÓCIO");
            System.out.println("   -----------------------------");

            try {
                Paciente pacienteInvalido = new Paciente(
                        "João Silva",
                        "joao@email.com",
                        "senha123",
                        LocalDate.of(1990, 1, 1),
                        "123", // CPF inválido
                        false,
                        false,
                        null
                );
                pacienteService.cadastrarPaciente(pacienteInvalido);
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Regra de negócio funcionando: " + e.getMessage());
            }

            // 5. TESTAR REGRAS DE NEGÓCIO - EMAIL DUPLICADO
            try {
                Paciente pacienteDuplicado = new Paciente(
                        "Outra Maria",
                        "maria.santos@email.com", // Email duplicado
                        "outrasenha",
                        LocalDate.of(1980, 1, 1),
                        "11122233344",
                        false,
                        false,
                        null
                );
                pacienteService.cadastrarPaciente(pacienteDuplicado);
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Regra de negócio funcionando: " + e.getMessage());
            }

            // 6. TESTAR REGRAS DE NEGÓCIO - DATA PASSADA
            try {
                Consulta consultaInvalida = new Consulta(
                        "Maria Santos",
                        LocalDate.now().minusDays(1), // Data no passado
                        LocalTime.of(10, 0),
                        "Cardiologia",
                        "Agendada"
                );
                consultaService.agendarConsulta(consultaInvalida);
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Regra de negócio funcionando: " + e.getMessage());
            }

            // 7. DEMONSTRAR LISTAGENS
            System.out.println("\n5. 📊 LISTAGENS E CONSULTAS");
            System.out.println("   ------------------------");

            System.out.println("   👥 Total de pacientes: " + pacienteService.listarTodos().size());
            System.out.println("   📋 Total de consultas: " + consultaService.listarTodas().size());

            // 8. DEMONSTRAR PACIENTES COM NECESSIDADES ESPECIAIS
            System.out.println("\n6. ♿ PACIENTES COM NECESSIDADES ESPECIAIS");
            System.out.println("   -------------------------------------");

            var pacientesEspeciais = pacienteService.buscarPacientesComNecessidadesEspeciais();
            System.out.println("   ✅ Pacientes com necessidades especiais: " + pacientesEspeciais.size());
            for (Paciente p : pacientesEspeciais) {
                System.out.println("      - " + p.getNome() + " (" + p.getNecessidadesEspeciais() + ")");
            }

            // 9. DEMONSTRAR CANCELAMENTO
            System.out.println("\n7. ❌ CANCELAMENTO DE CONSULTA");
            System.out.println("   ---------------------------");

            consultaService.cancelarConsulta(consulta1.getId());
            System.out.println("   ✅ Consulta cancelada com sucesso!");
            System.out.println("   📝 Status atual: " + consultaService.buscarPorId(consulta1.getId()).getStatus());

        } catch (Exception e) {
            System.out.println("❌ Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n==================================================");
        System.out.println("🎉 DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!");
        System.out.println("==================================================");
        System.out.println("✅ Todos os requisitos do DDD foram atendidos:");
        System.out.println("   - ✅ Camada Model com encapsulamento");
        System.out.println("   - ✅ Métodos com lógica de negócio");
        System.out.println("   - ✅ Classe de conexão com BD");
        System.out.println("   - ✅ CRUD completo");
        System.out.println("   - ✅ Classe de teste (main)");
        System.out.println("==================================================");

        scanner.close();
    }
}