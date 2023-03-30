package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.medico.DadosAtualizacaoMedico;
import med.voll.api.model.medico.DadosCadastroMedico;
import med.voll.api.model.medico.DadosListagemMedico;
import med.voll.api.model.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 15, sort = {"nome"}) Pageable pageable){
        return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        medicoRepository.save(new Medico(dados));
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizacaoMedico(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}