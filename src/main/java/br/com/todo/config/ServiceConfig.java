package br.com.todo.config;

import br.com.todo.service.AlarmeService;
import br.com.todo.service.TarefaService;

public class ServiceConfig {
    final AlarmeService alarmeService;
    final TarefaService tarefaService;

    public ServiceConfig(DaoConfig daoConfig) {
        alarmeService = new AlarmeService(
                daoConfig.alarmeDAO,
                daoConfig.tarefaDAO
        );
        tarefaService = new TarefaService(
                daoConfig.tarefaDAO,
                daoConfig.statusDAO
        );
    }

}
