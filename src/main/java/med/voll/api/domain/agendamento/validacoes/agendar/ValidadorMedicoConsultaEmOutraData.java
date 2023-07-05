package med.voll.api.domain.agendamento.validacoes.agendar;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.agendamento.ConsultaRepository;
import med.voll.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConsultaEmOutraData implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if(medicoPossuiOutraConsultaNoMesmoHorario)
            throw new ValidacaoException("Médico já possui outra consulta agendada neste mesmo horário");
    }
}
