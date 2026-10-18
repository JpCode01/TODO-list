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

    public MenuAlarme(Scanner scanner, AlarmeController alarmeController, TarefaController tarefaController, ValidationAlarme validationAlarme) {
        this.scanner = scanner;
        this.alarmeController = alarmeController;
        this.tarefaController = tarefaController;
        this.validationAlarme = validationAlarme;
    }

    public void escolherTarefaParaAlarme(List<TarefaViewDTO> tarefas) {
        System.out.println(tarefas);
        try {
            verificaSeHaTarefasDisponiveis(tarefas);
            Long idTarefaProcurada = solicitarId("Digite o ID da tarefa: ");
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
