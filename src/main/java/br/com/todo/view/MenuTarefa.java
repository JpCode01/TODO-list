package br.com.todo.view;

import br.com.todo.controller.StatusController;
import br.com.todo.controller.TarefaController;
import br.com.todo.dto.AtualizarTarefaDTO;
import br.com.todo.dto.CadastrarTarefaDTO;
import br.com.todo.dto.TarefaViewDTO;
import br.com.todo.exception.status.StatusNaoEncontradoException;
import br.com.todo.exception.tarefa.SemTarefasDisponiveisException;
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException;
import br.com.todo.model.Tarefa;
import br.com.todo.validation.ValidationTarefa;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuTarefa {
    private final Scanner scanner;
    private final TarefaController tarefaController;
    private final ValidationTarefa validationTarefa;
    private final StatusController statusController;

    public MenuTarefa(Scanner scanner, TarefaController tarefaController, ValidationTarefa validationTarefa, StatusController statusController) {
        this.scanner = scanner;
        this.tarefaController = tarefaController;
        this.validationTarefa = validationTarefa;
        this.statusController = statusController;
    }

    public void cadastrarTarefa() {
        CadastrarTarefaDTO cadastrarTarefaDTO = capturarDadosTarefa();
        tentarCadastrarTarefa(cadastrarTarefaDTO);
    }

    public void escolherTarefaParaAtualizar() {
        List<TarefaViewDTO> tarefas = listarTarefas();
        try {
            verificaSeHaTarefasDisponiveis(tarefas);
            Long idTarefaProcurada = solicitarId("Digite o ID da tarefa: ");
            Tarefa tarefaEncontrada = tarefaController.buscarTarefaPorId(idTarefaProcurada);

            atualizarTarefa(tarefaEncontrada);
        } catch (TarefaNaoEncontradaException | SemTarefasDisponiveisException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void escolherTarefaParaRemover() {
        List<TarefaViewDTO> tarefas = listarTarefas();
        try {
            verificaSeHaTarefasDisponiveis(tarefas);
            Long idTarefaProcurada = solicitarId("Digite o ID da tarefa: ");
            Tarefa tarefaEncontrada = tarefaController.buscarTarefaPorId(idTarefaProcurada);
            
            removerTarefa(tarefaEncontrada.getId());
        } catch (TarefaNaoEncontradaException | SemTarefasDisponiveisException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<TarefaViewDTO> listarTarefas() {
        try {
            return tarefaController.listarTarefas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listarTarefasAFazer() {
        try {
            System.out.println(tarefaController.listarTarefasAFazer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listarTarefasFazendo() {
        try {
            System.out.println(tarefaController.listarTarefasFazendo());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listarTarefasFeitas() {
        try {
            System.out.println(tarefaController.listarTarefasFeitas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removerTarefa(Long idTarefa) {
        try {
            tarefaController.removerTarefa(idTarefa);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void atualizarTarefa(Tarefa tarefa) {
        try {
            AtualizarTarefaDTO tarefaAtualizada = capturarDadosAtualizar(tarefa);
            tentarAtualizarTarefa(tarefaAtualizada);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tentarAtualizarTarefa(AtualizarTarefaDTO tarefaAtualizada) throws SQLException {
        try {
            tarefaController.atualizarTarefa(tarefaAtualizada);
            System.out.println("Tarefa atualizada com sucesso!");
        } catch (TarefaNaoEncontradaException | StatusNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private AtualizarTarefaDTO capturarDadosAtualizar(Tarefa tarefa) throws SQLException {
        System.out.println("Nome Tarefa: ");
        String nomeTarefa = scanner.nextLine();
        if (nomeTarefa.isBlank()) {
            nomeTarefa = tarefa.getNome();
        }

        System.out.println("Descricao Tarefa: ");
        String descricaoTarefa = scanner.nextLine();
        if (descricaoTarefa.isBlank()) {
            descricaoTarefa = tarefa.getDescricao();
        }

        System.out.println("Data limite Tarefa: ");
        String dataLimiteTarefa = scanner.nextLine();
        Date dataTermino = tarefa.getDataTermino();

        if (!dataLimiteTarefa.isBlank()) {
            dataTermino = Date.valueOf(
                    validationTarefa.validarData(dataLimiteTarefa, scanner)
            );
        }

        System.out.println("Prioridade Tarefa: ");
        String prioridadeTarefa = scanner.nextLine();
        int prioridade = tarefa.getPrioridade();

        if (!prioridadeTarefa.isBlank()) {
            prioridade = validationTarefa.validarPrioridade(prioridadeTarefa, scanner);
        }

        System.out.println("Categoria Tarefa: ");
        String categoriaTarefa = scanner.nextLine();
        if (categoriaTarefa.isBlank()) {
            categoriaTarefa = tarefa.getCategoria();
        }

        System.out.println("Status Tarefa: ");
        String statusTarefa = scanner.nextLine();
        if (statusTarefa.isBlank()) {
            statusTarefa = statusController.buscarNomePorId(tarefa.getIdStatus());
        }

        return new AtualizarTarefaDTO(
                tarefa.getId(),
                nomeTarefa,
                descricaoTarefa,
                dataTermino,
                prioridade,
                categoriaTarefa,
                statusTarefa
        );
    }

    private void verificaSeHaTarefasDisponiveis(List<TarefaViewDTO> tarefas) {
        if (tarefas.isEmpty()) {
            throw new SemTarefasDisponiveisException();
        }
    }

    private void tentarCadastrarTarefa(CadastrarTarefaDTO cadastrarTarefaDTO) {
        try {
            tarefaController.criarTarefa(cadastrarTarefaDTO);
        } catch (StatusNaoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CadastrarTarefaDTO capturarDadosTarefa() {
        System.out.println("Nome Tarefa: ");
        String nomeTarefa = scanner.nextLine();

        System.out.println("Descricao Tarefa: ");
        String descricaoTarefa = scanner.nextLine();

        System.out.println("Data limite: ");
        Date dataLimite = Date.valueOf(validationTarefa.validarData(scanner.nextLine(), scanner));

        System.out.println("Prioridade Tarefa: ");
        int prioridadeTarefa = validationTarefa.validarPrioridade(scanner.nextLine(),  scanner);

        System.out.println("Categoria Tarefa: ");
        String categoriaTarefa = scanner.nextLine();

        System.out.println("Status Tarefa: ");
        String status = scanner.nextLine();

        return new CadastrarTarefaDTO(
                nomeTarefa,
                descricaoTarefa,
                dataLimite,
                prioridadeTarefa,
                categoriaTarefa,
                status
        );
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
