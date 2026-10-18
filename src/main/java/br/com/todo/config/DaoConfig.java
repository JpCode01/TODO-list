package br.com.todo.config;

import br.com.todo.factory.database.ConnectionFactory;
import br.com.todo.factory.database.PostgresConnectionFactory;
import br.com.todo.repository.alarme.AlarmeDAO;
import br.com.todo.repository.status.StatusDAO;
import br.com.todo.repository.tarefa.TarefaDAO;

public class DaoConfig {
    
    final ConnectionFactory connectionFactory =
        new PostgresConnectionFactory();

    final TarefaDAO tarefaDAO =
            new TarefaDAO(connectionFactory);
    
    final AlarmeDAO alarmeDAO =
            new AlarmeDAO(connectionFactory);
    
    final StatusDAO statusDAO =
            new StatusDAO(connectionFactory);
}
