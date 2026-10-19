package br.com.todo.view;

import br.com.todo.controller.AlarmeController;
import br.com.todo.controller.TarefaController;
import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.exception.tarefa.SemTarefasDisponiveisException;
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException;
import br.com.todo.model.Alarme;
import br.com.todo.validation.ValidationAlarme;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuAlarme {
    private final Scanner scanner;
    private final AlarmeController alarmeController;
    private final TarefaController tarefaController;
    private final ValidationAlarme validationAlarme;
    private final EntradaConsole entradaConsole;

    public MenuAlarme(Scanner scanner, AlarmeController alarmeController, TarefaController tarefaController, ValidationAlarme validationAlarme, EntradaConsole entradaConsole) {
        this.scanner = scanner;
        this.alarmeController = alarmeController;
        this.tarefaController = tarefaController;
        this.validationAlarme = validationAlarme;
        this.entradaConsole = entradaConsole;
    }

    public void escolherTarefaParaAlarme(List<TarefaViewDTO> tarefas) {
        System.out.println(tarefas);
        try {
            verificaSeHaTarefasDisponiveis(tarefas);
            Long idTarefaProcurada = entradaConsole.solicitarId("Digite o ID da tarefa: ");
            tarefaController.buscarTarefaPorId(idTarefaProcurada);

            capturarDadosAlarme(idTarefaProcurada);
        } catch (TarefaNaoEncontradaException | SemTarefasDisponiveisException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verificaSeHaTarefasDisponiveis(List<TarefaViewDTO> tarefas) {
        if (tarefas.isEmpty()) {
            throw new SemTarefasDisponiveisException();
        }
    }

    private void capturarDadosAlarme(Long idTarefa) throws SQLException {
        System.out.println("Digite a quantidade de dias antes do prazo para disparo: ");
        String diasInput = scanner.nextLine();
        int dias = validationAlarme.validarDias(diasInput, scanner);

        criarAlarme(new Alarme(
                dias,
                idTarefa
        ));
    }

    private void criarAlarme(Alarme alarme) throws SQLException {
        alarmeController.criarAlarme(alarme);
    }
}
