package br.com.todo.config;

import br.com.todo.controller.AlarmeController;
import br.com.todo.controller.TarefaController;

public class ControllerConfig {

    private final AlarmeController alarmeController;
    private final TarefaController tarefaController;

    public ControllerConfig(ServiceConfig serviceConfig) {
        alarmeController = new AlarmeController(
                serviceConfig.alarmeService
        );
        
        tarefaController = new TarefaController(
                serviceConfig.tarefaService
        );
    }


}
