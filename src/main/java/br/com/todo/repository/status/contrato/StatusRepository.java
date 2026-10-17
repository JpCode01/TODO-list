package br.com.todo.repository.status.contrato;

import br.com.todo.model.Status;

import java.sql.SQLException;
import java.util.Optional;

public interface StatusRepository {
    Optional<Status> buscarPorNome(String nome) throws SQLException;
}
