package med.voll.api.domain.agendamento;

import med.voll.api.domain.Pacientes.PacientesRepository;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.agendamento.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.agendamento.validacoes.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if(!pacientesRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id não encontrado no banco de dadoos!");
        }
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id não encontrado no banco de dadoos!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacientesRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Médico indisponível para esta data.");

        }
        var consulta = new Consulta(null,medico,paciente,dados.data(), null);


        repository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória a partir do momento em que o id do médico não é fornecido.");
        }
        return medicoRepository.escolheMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!repository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = repository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
