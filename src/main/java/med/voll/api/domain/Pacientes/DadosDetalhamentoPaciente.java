package med.voll.api.domain.Pacientes;

import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(String nome, String email, String telefone, String cpf, Endereco endereco) {
    public DadosDetalhamentoPaciente(Pacientes paciente){
        this(paciente.getNome(),paciente.getEmail(),paciente.getTelefone(),paciente.getCpf(), paciente.getEndereco());
    }

}
