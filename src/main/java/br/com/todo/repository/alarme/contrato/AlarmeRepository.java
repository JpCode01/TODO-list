package br.com.todo.repository.alarme.contrato;

import br.com.todo.model.Alarme;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AlarmeRepository {
    void salvar(Alarme alarme) throws SQLException;
    
    void atualizar(Alarme alarme) throws SQLException;
    
    Optional<Alarme> buscarPorId(Long id) throws SQLException;

    void desativar(Long id) throws SQLException;

    void ativar(Long id) throws SQLException;
    
    List<Alarme> listarAlarmes() throws SQLException;
}
