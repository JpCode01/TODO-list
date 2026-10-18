package br.com.todo.controller;

import br.com.todo.model.Status;
import br.com.todo.service.StatusService;

import java.sql.SQLException;
import java.util.List;

public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }
    
    public String buscarNomePorId(Long idStatus) throws SQLException {
        return statusService.buscarPorId(idStatus);
    }

    public Long buscarIdPorNome(String nome) throws SQLException {
        return statusService.buscarIdPorNome(nome);
    }
    
    public List<Status> listarStatus() {
        return statusService.listarStatus();
    }


}
