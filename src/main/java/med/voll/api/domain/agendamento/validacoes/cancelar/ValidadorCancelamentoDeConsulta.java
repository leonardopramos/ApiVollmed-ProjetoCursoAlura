package med.voll.api.domain.agendamento.validacoes.cancelar;

import med.voll.api.domain.agendamento.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    public void validar(DadosCancelamentoConsulta dados);
}
