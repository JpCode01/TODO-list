package br.com.todo.repository.alarme;

import br.com.todo.factory.database.ConnectionFactory;
import br.com.todo.model.Alarme;
import br.com.todo.repository.alarme.contrato.AlarmeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlarmeDAO implements AlarmeRepository {

    private final ConnectionFactory connectionFactory;

    public AlarmeDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void salvar(Alarme alarme) throws SQLException {
        String sql = """
                INSERT INTO alarme
                    (id_tarefa,
                    ativo, 
                    antecedencias_dias_para_disparo)
                VALUES (?,?,?)
                """;

        try (
            Connection connection = connectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)
            )
        {
            statement.setLong(1, alarme.getIdTarefa());
            statement.setBoolean(2, alarme.isAtivo());
            statement.setInt(3, alarme.getAntecendenciaDiasParaDisparo());

            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                alarme.setId(generatedKeys.getLong(1));
            }
        }
    }

    @Override
    public void atualizar(Alarme alarme) throws SQLException {
        String sql = """
                UPDATE alarme
                SET id_tarefa = ?,
                    ativo = ?,  
                    antecedencias_dias_para_disparo = ?
                WHERE id_alarme = ?;
                """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setLong(1, alarme.getIdTarefa());
            statement.setBoolean(2, alarme.isAtivo());
            statement.setInt(3, alarme.getAntecendenciaDiasParaDisparo());
            
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Alarme> buscarPorId(Long id) throws SQLException {
        String sql = """
                SELECT id_alarme, id_tarefa, ativo, antecedencias_dias_para_disparo
                FROM alarme
                WHERE id_alarme = ?;
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
            
            Alarme alarme = new Alarme(
                    resultSet.getLong("id_alarme"),
                    resultSet.getBoolean("ativo"),
                    resultSet.getInt("antecedencias_dias_para_disparo"),
                    resultSet.getLong("id_tarefa")
            );
            
            return Optional.of(alarme);
        }
    }

    @Override
    public void desativar(Long id) throws SQLException {
        String sql = """
            UPDATE alarme
            SET ativo = false
            WHERE id_alarme = ?
            """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void ativar(Long id) throws SQLException {
        String sql = """
                UPDATE alarme
                SET ativo = true
                WHERE id_alarme = ?
                """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Alarme> listarAlarmes() throws SQLException {
        String sql = """
        SELECT
            a.id_alarme,
            a.id_tarefa,
            a.ativo,
            a.antecedencias_dias_para_disparo
        FROM alarme a
        JOIN tarefa t
            ON t.id_tarefa = a.id_tarefa
        JOIN status_tarefa s
            ON s.id_status_tarefa = t.id_status_tarefa
        WHERE a.ativo = TRUE
            AND s.nome <> 'DONE'
            AND CURRENT_DATE BETWEEN
                t.data_termino - a.antecedencias_dias_para_disparo
                AND t.data_termino
        """;

        List<Alarme> alarmes = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Alarme alarme = new Alarme(
                        resultSet.getLong("id_alarme"),
                        resultSet.getBoolean("ativo"),
                        resultSet.getInt("antecedencias_dias_para_disparo"),
                        resultSet.getLong("id_tarefa")
                );

                alarmes.add(alarme);
            }
        }

        return alarmes;
    }
}