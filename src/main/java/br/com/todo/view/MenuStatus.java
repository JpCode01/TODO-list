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

    public MenuStatus(Scanner scanner, TarefaController tarefaController, StatusController statusController) {
        this.scanner = scanner;
        this.tarefaController = tarefaController;
        this.statusController = statusController;
    }

    public void escolherTarefaParaAtualizarStatus(List<TarefaViewDTO> tarefas) {
        try {
            verificaSeHaTarefasDisponiveis(tarefas);
            Long idTarefaProcurada = solicitarId("Digite o ID da tarefa: ");
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
            Long idStatusBuscado = solicitarId("Digite o ID do Status: ");
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

    private Long solicitarId(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            String idEscolhido = scanner.nextLine();
            try {
                return Long.parseLong(idEscolhido);
            } catch (NumberFormatException e) {
                System.out.println("Erro, digite um ID númerico!");
            }
        }
    }
}
