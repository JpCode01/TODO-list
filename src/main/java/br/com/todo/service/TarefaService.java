package br.com.todo.service;

import br.com.todo.dto.CadastrarTarefaDTO;
import br.com.todo.repository.tarefa.contrato.TarefaRepostiory;

import java.io.IOException;

public class TarefaService {

    private final TarefaRepostiory tarefaRepostiory;

    public TarefaService(TarefaRepostiory tarefaRepostiory) {
        this.tarefaRepostiory = tarefaRepostiory;
    }

    public void criarTarefa(CadastrarTarefaDTO cadastrarTarefaDTO) throws IOException {

    }

    public void listarTarefa() throws IOException {
     
    }

    public void atualizarTarefa(String nome) throws IOException {
        
        
    }

    public void removerTarefa(String nome) throws IOException {
    }
}
