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
    public Optional<Status> buscarPorNome(String nome) throws SQLException {
        String sql = """
                SELECT id_status_tarefa,
                nome
                FROM status_tarefa
                WHERE nome = ? 
                """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, PreparedStatement.RETURN_GENERATED_KEYS)
                )
        {
            statement.setString(1, nome);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            Status status = new Status(
                    resultSet.getLong("id_status_tarefa"),
                    resultSet.getString("nome")
            );

            return Optional.of(status);

        }
    }
    

}
