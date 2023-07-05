package med.voll.api.domain.agendamento.validacoes.agendar;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var dataConsulta = dadosAgendamentoConsulta.data();
        var ehDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        if(ehDomingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento.");
        }
    }
}
