package br.com.todo.service;

import br.com.todo.exception.status.StatusNaoEncontradoException;
import br.com.todo.repository.status.contrato.StatusRepository;

import java.sql.SQLException;

public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public String buscarPorId(Long idStatus) throws SQLException {
        return statusRepository.buscarNomePorId(idStatus)
                .orElseThrow(() -> new StatusNaoEncontradoException(idStatus));
    }
}
