package br.com.todo.service;

import br.com.todo.exception.alarme.AlarmeDesativadoException;
import br.com.todo.exception.alarme.AlarmeNaoEncontradoException;
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException;
import br.com.todo.model.Alarme;
import br.com.todo.repository.alarme.contrato.AlarmeRepository;
import br.com.todo.repository.tarefa.contrato.TarefaRepostiory;

import java.sql.SQLException;

public class AlarmeService {

    private final AlarmeRepository alarmeRepository;
    private final TarefaRepostiory tarefaRepostiory;

    public AlarmeService(AlarmeRepository alarmeRepository, TarefaRepostiory tarefaRepostiory) {
        this.alarmeRepository = alarmeRepository;
        this.tarefaRepostiory = tarefaRepostiory;
    }

    public void criarAlarme(Alarme alarme) throws SQLException {
        tarefaRepostiory.buscarPorId(alarme.getIdTarefa())
                .orElseThrow(() -> new TarefaNaoEncontradaException(alarme.getIdTarefa()));
        
        alarmeRepository.salvar(alarme);
    }
    
    public void atualizarAlarme(Alarme alarme) throws  SQLException {
        if (!alarme.isAtivo()) {
            throw new AlarmeDesativadoException();
        }

        alarmeRepository.buscarPorId(alarme.getId())
                .orElseThrow(() -> new AlarmeNaoEncontradoException(alarme.getId()));
        
        alarmeRepository.atualizar(alarme);
    }
    
    public void desativarAlarme(Long idAlarme) throws SQLException {
        alarmeRepository.buscarPorId(idAlarme)
                .orElseThrow(() -> new AlarmeNaoEncontradoException(idAlarme));
    
        alarmeRepository.desativar(idAlarme);
    }

    public void ativarAlarme(Long idAlarme) throws SQLException {
        alarmeRepository.buscarPorId(idAlarme)
                .orElseThrow(() -> new AlarmeNaoEncontradoException(idAlarme));

        alarmeRepository.ativar(idAlarme);
    }
}
