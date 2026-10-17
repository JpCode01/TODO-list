package br.com.todo.repository.tarefa;

import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.factory.database.ConnectionFactory;
import br.com.todo.model.Tarefa;
import br.com.todo.repository.tarefa.contrato.TarefaRepostiory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TarefaDAO implements TarefaRepostiory {

    private final ConnectionFactory connectionFactory;

    public TarefaDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) throws SQLException {
        String sql = """
                INSERT INTO tarefa
                    (nome, descricao, data_termino, prioridade,
                    categoria, id_status_tarefa)
                values (?, ?, ?, ?, ?, ?)
                """;
        
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, PreparedStatement.RETURN_GENERATED_KEYS)
        )
        {
            statement.setString(1, tarefa.getNome());
            statement.setString(2, tarefa.getDescricao());
            statement.setDate(3, tarefa.getDataTermino());
            statement.setInt(4, tarefa.getPrioridade());
            statement.setString(5, tarefa.getCategoria());
            statement.setLong(6, tarefa.getIdStatus());

            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                tarefa.setId(generatedKeys.getLong(1));
            }

            return tarefa;
        }
    }

    @Override
    public Optional<Tarefa> buscarPorId(Long id) throws SQLException {
        String sql = """
                    SELECT id_tarefa, nome, descricao, data_termino,
                    prioridade, categoria, id_satatus_tarefa
                    FROM tarefa
                    WHERE id_tarefa = ?
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
            
            Tarefa tarefa = new Tarefa(
                    resultSet.getLong("id_tarefa"),
                    resultSet.getString("nome"),
                    resultSet.getString("descricao"),
                    resultSet.getDate("data_termino"),
                    resultSet.getInt("prioridade"),
                    resultSet.getString("categoria"),
                    resultSet.getLong("id_satatus_tarefa")
            );
            
            return Optional.of(tarefa);
        }
    }

    @Override
    public void atualizar(Tarefa tarefa) throws SQLException {
        String sql = """
                UPDATE tarefa
                SET nome = ?,
                    descricao = ?,
                    data_termino = ?,
                    prioridade = ?,
                    categoria = ?,
                    id_status_tarefa = ?
                WHERE id = ?
                """;
        
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) { 
            statement.setString(1, tarefa.getNome());
            statement.setString(2, tarefa.getDescricao());
            statement.setDate(3, tarefa.getDataTermino());
            statement.setInt(4, tarefa.getPrioridade());
            statement.setString(5, tarefa.getCategoria());
            statement.setLong(6, tarefa.getIdStatus());

            statement.executeUpdate();
        }
    }

    @Override
    public void remover(Long id) throws SQLException {
        String sql = """
                DELETE FROM tarefa
                WHERE id = ?
                """;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        )
        {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<TarefaViewDTO> listarTarefas() throws SQLException {
        String sql = """
            SELECT
                t.id,
                t.nome,
                t.descricao,
                t.data_termino,
                t.prioridade,
                t.categoria,
                s.nome AS status
            FROM tarefa t
            JOIN status_tarefa s
                ON t.id_status_tarefa = s.id_status_tarefa
            """;

        List<TarefaViewDTO> tarefas = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

            )
        {
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                TarefaViewDTO tarefa = new TarefaViewDTO(
                        resultSet.getLong("id_tarefa"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        resultSet.getDate("data_termino"),
                        resultSet.getInt("prioridade"),
                        resultSet.getString("categoria"),
                        resultSet.getString("status")
                );

                tarefas.add(tarefa);
            }

        }

        return tarefas;
    }
    
    @Override
    public List<TarefaViewDTO> listarTarefasAFazer() throws SQLException {
        String sql = """
            SELECT
                t.id,
                t.nome,
                t.descricao,
                t.data_termino,
                t.prioridade,
                t.categoria,
                s.nome AS status
            FROM tarefa t
            JOIN status_tarefa s
                ON t.id_status_tarefa = s.id_status_tarefa
            """;

        List<TarefaViewDTO> tarefas = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

        )
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TarefaViewDTO tarefa = new TarefaViewDTO(
                        resultSet.getLong("id_tarefa"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        resultSet.getDate("data_termino"),
                        resultSet.getInt("prioridade"),
                        resultSet.getString("categoria"),
                        resultSet.getString("status")
                );

                tarefas.add(tarefa);
            }

        }

        return tarefas;
    }

    @Override
    public List<TarefaViewDTO> listarTarefasFazendo() throws SQLException {
        String sql = """
            SELECT
                t.id,
                t.nome,
                t.descricao,
                t.data_termino,
                t.prioridade,
                t.categoria,
                s.nome AS status
            FROM tarefa t
            JOIN status_tarefa s
                ON t.id_status_tarefa = s.id_status_tarefa
            """;

        List<TarefaViewDTO> tarefas = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        )
        {

            while (resultSet.next()) {
                TarefaViewDTO tarefa = new TarefaViewDTO(
                        resultSet.getLong("id_tarefa"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        resultSet.getDate("data_termino"),
                        resultSet.getInt("prioridade"),
                        resultSet.getString("categoria"),
                        resultSet.getString("status")
                );

                tarefas.add(tarefa);
            }

        }

        return tarefas;
    }

    @Override
    public List<TarefaViewDTO> listarTarefasFeitas() throws SQLException {
        String sql = """
            SELECT
                t.id,
                t.nome,
                t.descricao,
                t.data_termino,
                t.prioridade,
                t.categoria,
                s.nome AS status
            FROM tarefa t
            JOIN status_tarefa s
                ON t.id_status_tarefa = s.id_status_tarefa
            """;

        List<TarefaViewDTO> tarefas = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        )
        {

            while (resultSet.next()) {
                TarefaViewDTO tarefa = new TarefaViewDTO(
                        resultSet.getLong("id_tarefa"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        resultSet.getDate("data_termino"),
                        resultSet.getInt("prioridade"),
                        resultSet.getString("categoria"),
                        resultSet.getString("status")
                );

                tarefas.add(tarefa);
            }

        }

        return tarefas;
    }
}
