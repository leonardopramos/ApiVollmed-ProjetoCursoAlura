package med.voll.api.domain.Pacientes;

public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {


    public DadosListagemPaciente (Pacientes pacientes){
        this(pacientes.getId(), pacientes.getNome(), pacientes.getEmail(), pacientes.getCpf());
    }
}
