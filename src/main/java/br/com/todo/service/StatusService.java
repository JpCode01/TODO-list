package br.com.todo.service;

import br.com.todo.exception.status.StatusNaoEncontradoException;
import br.com.todo.model.Status;
import br.com.todo.repository.status.contrato.StatusRepository;

import java.sql.SQLException;
import java.util.List;

public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public String buscarPorId(Long idStatus) throws SQLException {
        return statusRepository.buscarNomePorId(idStatus)
                .orElseThrow(() -> new StatusNaoEncontradoException(idStatus));
    }

    public Long buscarIdPorNome(String nomeStatus) throws SQLException {
        return statusRepository.buscarIdPorNome(nomeStatus)
                .orElseThrow(() -> new StatusNaoEncontradoException(nomeStatus));
    }

    public List<Status> listarStatus() {
        try {
            return statusRepository.listarStatus();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
