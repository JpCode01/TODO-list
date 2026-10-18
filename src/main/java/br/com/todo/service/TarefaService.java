package br.com.todo.service;

import br.com.todo.dto.AtualizarTarefaDTO;
import br.com.todo.dto.CadastrarTarefaDTO;
import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.exception.status.StatusNaoEncontradoException;
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException;
import br.com.todo.model.Tarefa;
import br.com.todo.repository.status.contrato.StatusRepository;
import br.com.todo.repository.tarefa.contrato.TarefaRepostiory;

import java.sql.SQLException;
import java.util.List;

public class TarefaService {

    private final TarefaRepostiory tarefaRepostiory;
    private final StatusRepository statusRepository;

    public TarefaService(TarefaRepostiory tarefaRepostiory, StatusRepository statusRepository) {
        this.tarefaRepostiory = tarefaRepostiory;
        this.statusRepository = statusRepository;
    }

    public void criarTarefa(CadastrarTarefaDTO cadastrarTarefaDTO) throws SQLException {
        Long idStatus = statusRepository.buscarIdPorNome(cadastrarTarefaDTO.status())
                .orElseThrow(() -> new StatusNaoEncontradoException(cadastrarTarefaDTO.status()));
        
        tarefaRepostiory.salvar(
                new Tarefa(
                    cadastrarTarefaDTO.nome(),
                    cadastrarTarefaDTO.descricao(),
                    cadastrarTarefaDTO.dataTermino(),
                    cadastrarTarefaDTO.prioridade(),
                    cadastrarTarefaDTO.categoria(),
                    idStatus
                )
        );
    }

    public Tarefa buscarTarefaPorId(Long id) throws SQLException {
        return tarefaRepostiory.buscarPorId(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));
    }

    public List<TarefaViewDTO> listarTarefas() throws SQLException {
        return tarefaRepostiory.listarTarefas();
    }

    public List<TarefaViewDTO> listarTarefasAFazer() throws SQLException {
        return tarefaRepostiory.listarTarefasAFazer();
    }

    public List<TarefaViewDTO> listarTarefasFazendo() throws SQLException {
        return tarefaRepostiory.listarTarefasFazendo();
    }

    public List<TarefaViewDTO> listarTarefasFeitas() throws SQLException {
        return tarefaRepostiory.listarTarefasFeitas();
    }

    public void atualizarTarefa(AtualizarTarefaDTO atualizarTarefaDTO) throws SQLException {
        tarefaRepostiory.buscarPorId(atualizarTarefaDTO.id())
                .orElseThrow(() -> new TarefaNaoEncontradaException(atualizarTarefaDTO.id()));

        Long idStatus = statusRepository.buscarIdPorNome(atualizarTarefaDTO.status())
                .orElseThrow(() -> new StatusNaoEncontradoException(atualizarTarefaDTO.status()));

        tarefaRepostiory.atualizar(
                new Tarefa(
                        atualizarTarefaDTO.id(),
                        atualizarTarefaDTO.nome(), 
                        atualizarTarefaDTO.descricao(),
                        atualizarTarefaDTO.dataTermino(),
                        atualizarTarefaDTO.prioridade(),
                        atualizarTarefaDTO.categoria(),
                        idStatus
                )
        );
    }

    public void removerTarefa(Long id) throws SQLException {
        tarefaRepostiory.buscarPorId(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));

        tarefaRepostiory.remover(id);
    }

    public void atualizarStatus(Long idTarefa, Long idStatusNovo) throws  SQLException {
        tarefaRepostiory.atualizarStatus(idTarefa, idStatusNovo);
    }
}
