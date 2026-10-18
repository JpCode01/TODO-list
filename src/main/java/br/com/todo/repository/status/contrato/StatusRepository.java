package br.com.todo.repository.status.contrato;

import br.com.todo.model.Status;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StatusRepository {
    Optional<Long> buscarIdPorNome(String nome) throws SQLException;
    Optional<String> buscarNomePorId(Long id) throws SQLException;

    List<Status> listarStatus() throws SQLException;
}
