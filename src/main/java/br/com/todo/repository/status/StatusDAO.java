package br.com.todo.repository.status;

import br.com.todo.factory.database.ConnectionFactory;
import br.com.todo.model.Status;
import br.com.todo.repository.status.contrato.StatusRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Optional<String> buscarNomePorId(Long id) throws SQLException {
        String sql = """
            SELECT nome
            FROM status_tarefa
            WHERE id_status_tarefa = ?
            """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(resultSet.getString("nome"));
        }
    }

    @Override
    public List<Status> listarStatus() throws SQLException {
        String sql = """
            SELECT id_status_tarefa, nome
            FROM status_tarefa
            """;

        List<Status> statusList = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Status status = new Status(
                        resultSet.getLong("id_status_tarefa"),
                        resultSet.getString("nome")
                );

                statusList.add(status);
            }
        }

        return statusList;
    }


}
