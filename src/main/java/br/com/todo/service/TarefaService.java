package br.com.todo.service;


import br.com.todo.enums.Status;
import br.com.todo.model.Tarefa;
import br.com.todo.validation.ValidationTarefa;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TarefaService {
    private final File arquivo = new File("tarefas.json");
    private final ValidationTarefa validationTarefa = new ValidationTarefa();
    private Scanner scanner =  new Scanner(System.in);
    private ObjectMapper mapper = new ObjectMapper();

    public void inserirTarefa() throws IOException {
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        if(!temTarefaPorNome(nome)) {
            
            System.out.println("Descrição: ");
            String descricao = scanner.nextLine();
            int prioridade;
            
            while (true) {
                System.out.println("Prioridade (1 a 5): ");
                prioridade = scanner.nextInt();
                if (!validationTarefa.prioridadeValida(prioridade)) {
                    System.out.println("Opção inválida! Digite um número de 1 a 5");
                } else {
                    break;
                }
            }
            scanner.nextLine();

            System.out.println("Categoria: ");
            String categoria = scanner.nextLine();
            String status;
            while (true) {
                System.out.println("Status: ");
                status = scanner.nextLine().toUpperCase();
                if (!validationTarefa.statusValido(status)) {
                    System.out.println("Opção inválida! Digite uma opção entre TODO, DOING ou DONE");
                } else {
                    break;
                }

            }

            String dataTermino;
            while (true) {
                System.out.println("Nova data de termino (aaaa-mm-dd):");
                dataTermino = scanner.nextLine();
                if (!validationTarefa.dataValida(dataTermino)) {
                    System.out.println("Data inválida! use o formato aaaa-mm-dd");
                } else {
                    break;
                }
            }
            Tarefa tarefa = new Tarefa(nome, descricao, LocalDate.parse(dataTermino), prioridade, categoria, Status.valueOf(status));

            List<Tarefa> tarefas = carregarTarefa();
            tarefas.add(tarefa);
            rebalancearPrioridades(tarefas);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(arquivo, tarefas);
            System.out.println("Tarefa adicionada com sucesso!");
        } else {
            System.out.println("Já existe uma tarefa com esse nome!");
        }
    }
    

    public List<Tarefa> carregarTarefa() throws IOException {
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        return mapper.readValue(
                arquivo,
                new TypeReference<List<Tarefa>>() {
                }
        );
    }

    public void listarTarefa() throws IOException {
        List<Tarefa> tarefas = carregarTarefa();
        for (Tarefa tarefa : tarefas) {
            System.out.println(tarefa);
        }
    }

    public String atualizarTarefa(String nome) throws IOException {
        // Para contextualizar para você que tiver vendo isso kkkkkkkkk:
        // Vázio -> Igual era antes
        // Não vázio -> Modifica (Caso não haja validações)
        List<Tarefa> tarefas = carregarTarefa();
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Novo nome:");
                String novoNome = scanner.nextLine();
                if(!novoNome.equalsIgnoreCase(nome) && !temTarefaPorNome(novoNome) && !novoNome.equals("")) {
                    tarefa.setNome(novoNome);
                } else if (temTarefaPorNome(novoNome)) {
                    System.out.println("Tarefa já existente!");
                }

                System.out.println("Nova descrição:");
                String novaDescricao = scanner.nextLine();
                if (!novaDescricao.equals("")) {

                    tarefa.setDescricao(novaDescricao);
                }

                while (true) {
                    System.out.println("Nova prioridade: ");
                    String prioridade = scanner.nextLine();
                    if (!prioridade.equals("") && !validationTarefa.prioridadeValida(Integer.parseInt(prioridade))) {
                        System.out.println("Opção inválida! Digite um número de 1 a 5");
                    } else if (!prioridade.equals("")) {
                        tarefa.setPrioridade(Integer.parseInt(prioridade));
                        break;
                    } else {
                        break;
                    }
                }
                scanner.nextLine();

                System.out.println("Nova categoria:");
                String novaCategoria = scanner.nextLine();
                if (!novaCategoria.equals("")) {
                    tarefa.setCategoria(scanner.nextLine());
                }

                while (true) {
                    System.out.println("Novo status: ");
                    String status = scanner.nextLine();
                    if (!status.equals("") && !validationTarefa.statusValido(status)) {
                        System.out.println("Opção inválida! Digite uma opção entre TODO, DOING ou DONE");
                    } else if (!status.equals("")) {
                        tarefa.setStatus(Status.valueOf(status));
                        break;
                    } else {
                        break;
                    }

                }

                while (true) {
                    System.out.println("Nova data de termino (aaaa-mm-dd):");
                    String dataTermino = scanner.nextLine();
                    if (!dataTermino.equals("") && !validationTarefa.dataValida(dataTermino)) {
                        System.out.println("Data inválida! use o formato aaaa-mm-dd");
                    } else if (!dataTermino.equals("")) {
                        tarefa.setDataTermino(LocalDate.parse(dataTermino));
                        break;
                    } else {
                        break;
                    }
                }
                rebalancearPrioridades(tarefas);

                mapper.writerWithDefaultPrettyPrinter()
                        .writeValue(arquivo, tarefas);

                return "Tarefa atualizada.";
            }
        }
        return "Essa tarefa não existe.";

    }

    public void removerTarefa(String nome) throws IOException {
        List<Tarefa> tarefas = carregarTarefa();
        boolean removida = tarefas.removeIf(tarefa -> tarefa.getNome().equalsIgnoreCase(nome));
        if (removida) {
            rebalancearPrioridades(tarefas);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(arquivo, tarefas);
            System.out.println("Tarefa removida.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }
    public boolean temTarefaPorNome(String nome) throws IOException {
        List<Tarefa> tarefas = carregarTarefa();
        return tarefas.stream().anyMatch(tarefa -> tarefa.getNome().equalsIgnoreCase(nome));
    }

    private void rebalancearPrioridades(List<Tarefa> tarefas) {
        tarefas.sort((t1, t2) ->
                Integer.compare(t2.getPrioridade(), t1.getPrioridade()));
    }

    public void listarPorPrioridade(int prioridade) throws IOException {
        List<Tarefa> tarefas = carregarTarefa();
        tarefas.stream()
                .filter(t -> t.getPrioridade() == prioridade)
                .forEach(System.out::println);
    }

    public void listarPorCategoria(String categoria) throws IOException {
        List<Tarefa> tarefas = carregarTarefa();
        tarefas.stream()
                .filter(t -> t.getCategoria().equalsIgnoreCase(categoria))
                .forEach(System.out::println);
    }

    public void listarPorStatus(String status) throws IOException {
        List<Tarefa> tarefas = carregarTarefa();
        tarefas.stream()
                .filter(t -> t.getStatus() == Status.valueOf(status.toUpperCase()))
                .forEach(System.out::println);
    }
}
