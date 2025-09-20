CREATE TABLE IF NOT EXISTS pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    deficiencia_visual BOOLEAN DEFAULT FALSE,
    deficiencia_auditiva BOOLEAN DEFAULT FALSE,
    necessidades_especiais TEXT
);

CREATE TABLE IF NOT EXISTS consultas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_paciente VARCHAR(255) NOT NULL,
    data_consulta DATE NOT NULL,
    hora_consulta TIME NOT NULL,
    especialidade VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'Agendada'
);