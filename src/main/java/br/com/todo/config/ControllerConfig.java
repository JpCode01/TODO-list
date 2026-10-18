package br.com.todo.config;

import br.com.todo.controller.AlarmeController;
import br.com.todo.controller.StatusController;
import br.com.todo.controller.TarefaController;

public class ControllerConfig {

    public final AlarmeController alarmeController;
    public final TarefaController tarefaController;
    public final StatusController statusController;

    public ControllerConfig(ServiceConfig serviceConfig) {
        alarmeController = new AlarmeController(
                serviceConfig.alarmeService
        );

        tarefaController = new TarefaController(
                serviceConfig.tarefaService
        );

        statusController = new StatusController(
                serviceConfig.statusService
        );
    }
}