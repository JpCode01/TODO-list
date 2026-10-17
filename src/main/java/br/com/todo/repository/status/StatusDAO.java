package br.com.todo.repository.status;

import br.com.todo.factory.database.ConnectionFactory;
import br.com.todo.model.Status;
import br.com.todo.repository.status.contrato.StatusRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StatusDAO implements StatusRepository {

    private final ConnectionFactory connectionFactory;

    public StatusDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Optional<Long> buscarIdPorNome(String nome) throws SQLException {
        String sql = """
            SELECT id_status_tarefa
            FROM status_tarefa
            WHERE nome = ?
            """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, nome);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(resultSet.getLong("id_status_tarefa"));
        }
    }
    

}
