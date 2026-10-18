package br.com.todo.view;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    MenuTarefa menuTarefa;
    
    void inicio() {
        System.out.println("""
        SEJA BEM VINDO AO TODO LIST, AQUI VOCE PODE CRIAR UMA LISTA DE 
        TAREFAS E MODIFICAR SEU ESTADO ENTRE (TODO, DOING E DONE)
        
        VOCE TAMBEM PODE EDITAR NOMES, DESCRICOES E MUITO MAIS.
        """);
        
        while (true) {
            int escolha = capturarEscolha("""
            1 - ADICIONAR TAREFA
            2 - LISTAR TODAS AS TAREFAS
            3 - LISTAR TAREFAS A FAZER
            4 - LISTAR TAREFAS SENDO FEITAS
            5 - LISTAR TAREFAS JA FEITAS
            6 - ATUALIZAR TAREFA
            7 - REMOVER TAREFA
            0 - SAIR
            
            ESCOLHA A OPCAO DESEJADA:""");
            switch (escolha) {
                case 1:
                    menuTarefa.cadastrarTarefa();
                    break;
                case 2:
                    System.out.println(menuTarefa.listarTarefas());
                    break;
                case 3:
                    menuTarefa.listarTarefasFazendo();
                    break;
                case 4:
                    menuTarefa.listarTarefasAFazer();
                    break;
                case 5:
                    menuTarefa.listarTarefasFeitas();
                    break;
                case 6:
                    menuTarefa.escolherTarefaParaAtualizar();
                    break;
                case 7:
                    menuTarefa.escolherTarefaParaRemover();
                    break;
                case 0:
                    return;
            }
        }
        
    }

    private int capturarEscolha(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            String opcaoUsuario = scanner.nextLine();
            try {
                return Integer.parseInt(opcaoUsuario);
            } catch (NumberFormatException e) {
                System.out.println("Erro, Digite uma opção númerica inteira!");
            }
        }
    }

}
