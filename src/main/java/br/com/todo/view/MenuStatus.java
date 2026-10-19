package br.com.todo.view;

import br.com.todo.controller.StatusController;
import br.com.todo.controller.TarefaController;
import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.exception.status.StatusNaoEncontradoException;
import br.com.todo.exception.tarefa.SemTarefasDisponiveisException;
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException;
import br.com.todo.model.Status;
import br.com.todo.model.Tarefa;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuStatus {

    private final Scanner scanner;
    private final TarefaController tarefaController;
    private final StatusController statusController;
    private final EntradaConsole entradaConsole;

    public MenuStatus(Scanner scanner, TarefaController tarefaController, StatusController statusController, EntradaConsole entradaConsole) {
        this.scanner = scanner;
        this.tarefaController = tarefaController;
        this.statusController = statusController;
        this.entradaConsole = entradaConsole;
    }

    public void escolherTarefaParaAtualizarStatus(List<TarefaViewDTO> tarefas) {
        System.out.println(tarefas);
        try {
            verificaSeHaTarefasDisponiveis(tarefas);
            Long idTarefaProcurada = entradaConsole.solicitarId("Digite o ID da tarefa: ");
            Tarefa tarefaEncontrada = tarefaController.buscarTarefaPorId(idTarefaProcurada);

            capturarNovoStatus(tarefaEncontrada.getId());
        } catch (TarefaNaoEncontradaException | SemTarefasDisponiveisException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void atualizarStatus(Long idTarefa, Long idStatus) throws SQLException {
        tarefaController.atualizarStatus(idTarefa, idStatus);
    }

    private void capturarNovoStatus(Long idTarefa) {
        List<Status> status = statusController.listarStatus();
        System.out.println(status);
        try {
            Long idStatusBuscado = entradaConsole.solicitarId("Digite o ID do Status: ");
            statusController.buscarNomePorId(idStatusBuscado);
            
            atualizarStatus(idTarefa, idStatusBuscado);
        } catch (StatusNaoEncontradoException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verificaSeHaTarefasDisponiveis(List<TarefaViewDTO> tarefas) {
        if (tarefas.isEmpty()) {
            throw new SemTarefasDisponiveisException();
        }
    }
}
