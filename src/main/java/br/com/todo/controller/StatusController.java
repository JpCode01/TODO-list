package br.com.todo.controller;

import br.com.todo.service.StatusService;

import java.sql.SQLException;

public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }
    
    public String buscarNomePorId(Long idStatus) throws SQLException {
        return statusService.buscarPorId(idStatus);
    }
}
