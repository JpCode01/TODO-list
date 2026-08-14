package br.com.todo.view;
import br.com.todo.service.TarefaService;
import br.com.todo.validation.ValidationTarefa;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final TarefaService tarefaService = new TarefaService();
    private final ValidationTarefa validationTarefa = new ValidationTarefa();
    public void inicio() throws IOException {
        while (true) {
            tarefaService.verificarAlarmes();
            System.out.println("""
                    SEJA BEM VINDO AO TODO LIST, AQUI VOCE PODE CRIAR UMA LISTA DE 
                    TAREFAS E MODIFICAR SEU ESTADO ENTRE (TODO, DOING E DONE)
                    
                    VOCE TAMBEM PODE EDITAR NOMES, DESCRICOES E MUITO MAIS.
                    
                    ESCOLHA A OPCAO DESEJADA:
                    
                    1 - ADICIONAR TAREFA
                    2 - LISTAR TODAS AS TAREFAS
                    3 - LISTAR POR PRIORIDADE
                    4 - LISTAR POR CATEGORIA
                    5 - LISTAR POR STATUS
                    6 - ATUALIZAR POR NOME
                    7 - REMOVER POR NOME
                    0 - SAIR
                    """);
            switch (scanner.nextInt()) {
                case 1:
                    tarefaService.inserirTarefa();
                    break;
                case 2:
                    tarefaService.listarTarefa();
                    break;
                case 3:
                    scanner.nextLine();
                    while (true) {
                        System.out.println("Prioridade desejada: ");
                        String prioridade = scanner.nextLine();
                        if (prioridade.equals("") || !validationTarefa.prioridadeValida(Integer.parseInt(prioridade))) {
                            System.out.println("Opção inválida! Digite um número de 1 a 5");
                        } else {
                            tarefaService.listarPorPrioridade(Integer.parseInt(prioridade));
                            break;
                        }
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    while (true) {
                        System.out.println("Categoria desejada: ");
                        String categoria = scanner.nextLine();
                        if (categoria.equals("")) {
                            System.out.println("Opção inválida! A categoria não pode ser vázia");
                        } else {
                            tarefaService.listarPorCategoria(categoria);
                            break;
                        }
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    while (true) {
                        System.out.println("Status desejado: ");
                        String status = scanner.nextLine();
                        if (status.equals("") || !validationTarefa.statusValido(status)) {
                            System.out.println("Opção inválida! Digite uma opção entre TODO, DOING ou DONE");
                        } else {
                            tarefaService.listarPorStatus(status);
                            break;
                        }
                    }
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.println("Digite o nome da tarefa que deseja atualizar: ");
                    tarefaService.atualizarTarefa(scanner.nextLine());
                    break;
                case 7:
                    scanner.nextLine();
                    System.out.println("Digite o nome da tarefa que deseja remover: ");
                    tarefaService.removerTarefa(scanner.nextLine());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}
