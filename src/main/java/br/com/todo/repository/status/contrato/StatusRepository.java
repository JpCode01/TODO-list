package br.com.todo.repository.status.contrato;

import java.sql.SQLException;
import java.util.Optional;

public interface StatusRepository {
    Optional<Long> buscarIdPorNome(String nome) throws SQLException;
    Optional<String> buscarNomePorId(Long id) throws SQLException;
}
