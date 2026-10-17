package br.com.todo.repository.tarefa.contrato;

import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.model.Tarefa;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TarefaRepostiory {
    Tarefa salvar(Tarefa tarefa) throws SQLException;
    
    Optional<Tarefa> buscarPorId(Long id) throws SQLException;

    void atualizar(Tarefa tarefa) throws SQLException;

    void remover(Long id) throws SQLException;

    List<TarefaViewDTO> listarTarefas() throws SQLException;

    List<TarefaViewDTO> listarTarefasAFazer() throws SQLException;

    List<TarefaViewDTO> listarTarefasFazendo() throws SQLException;

    List<TarefaViewDTO> listarTarefasFeitas() throws SQLException;
}
