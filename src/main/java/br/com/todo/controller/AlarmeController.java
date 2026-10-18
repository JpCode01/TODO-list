package br.com.todo.controller;

import br.com.todo.model.Alarme;
import br.com.todo.service.AlarmeService;

import java.sql.SQLException;
import java.util.List;

public class AlarmeController {

    private final AlarmeService alarmeService;

    public AlarmeController(AlarmeService alarmeService) {
        this.alarmeService = alarmeService;
    }

    public void criarAlarme(Alarme alarme) throws SQLException {
        this.alarmeService.criarAlarme(alarme);
    }

    public void atualizarAlarme(Alarme alarme) throws SQLException {
        this.alarmeService.atualizarAlarme(alarme);
    }
    
    public void desativarAlarme(Long idAlarme) throws SQLException {
        this.alarmeService.desativarAlarme(idAlarme);
    }

    public void ativarAlarme(Long idAlarme) throws SQLException {
        this.alarmeService.ativarAlarme(idAlarme);
    }

    public List<Alarme> listarAlarmes() throws SQLException {
        return this.alarmeService.listarAlarmes();
    }
}
