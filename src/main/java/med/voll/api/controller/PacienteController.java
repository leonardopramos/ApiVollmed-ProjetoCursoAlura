package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.Pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacientesRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        repository.save(new Pacientes(dados));
    }
    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = "cpf")Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
    }
    @PutMapping
    @Transactional
    public void alteraDados(@RequestBody@Valid DadosAtualizacaoPacientes dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }
}