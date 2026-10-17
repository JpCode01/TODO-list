package br.com.todo.controller;

import br.com.todo.dto.AtualizarTarefaDTO;
import br.com.todo.dto.CadastrarTarefaDTO;
import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.service.TarefaService;

import java.sql.SQLException;
import java.util.List;

public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    public void criarTarefa(CadastrarTarefaDTO cadastrarTarefaDTO) throws SQLException {
        tarefaService.criarTarefa(cadastrarTarefaDTO);
    }

    public void atualizarTarefa(AtualizarTarefaDTO atualizarTarefaDTO) throws SQLException {
        tarefaService.atualizarTarefa(atualizarTarefaDTO);
    }

    public List<TarefaViewDTO> listarTarefas() throws SQLException {
        return tarefaService.listarTarefas();
    }

    public List<TarefaViewDTO> listarTarefasAFazer() throws SQLException {
        return tarefaService.listarTarefasAFazer();
    }

    public List<TarefaViewDTO> listarTarefasFazendo() throws SQLException {
        return tarefaService.listarTarefasFazendo();
    }

    public List<TarefaViewDTO> listarTarefasFeitas() throws SQLException {
        return tarefaService.listarTarefasFeitas();
    }

    


}
