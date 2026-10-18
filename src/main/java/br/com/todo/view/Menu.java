package br.com.todo.view;

import br.com.todo.config.MenuConfig;
import br.com.todo.controller.AlarmeController;
import br.com.todo.factory.menu.MenuFactory;
import br.com.todo.model.Alarme;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final MenuFactory menuFactory = new MenuFactory(scanner);

    private final MenuConfig menuConfig = menuFactory.getMenuConfig();
    private final AlarmeController alarmeController =
            menuFactory.getControllerConfig().alarmeController;

    private final MenuTarefa menuTarefa = menuConfig.menuTarefa;
    private final MenuStatus menuStatus = menuConfig.menuStatus;
    private final MenuAlarme menuAlarme = menuConfig.menuAlarme;
    
    public void inicio() {
        System.out.println("""
        SEJA BEM VINDO AO TODO LIST, AQUI VOCE PODE CRIAR UMA LISTA DE 
        TAREFAS E MODIFICAR SEU ESTADO ENTRE (TODO, DOING E DONE)
        
        VOCE TAMBEM PODE EDITAR NOMES, DESCRICOES E MUITO MAIS.
        """);
        
        while (true) {
            dispararAlarmes();
            int escolha = capturarEscolha("""
            1 - ADICIONAR TAREFA
            2 - LISTAR TODAS AS TAREFAS
            3 - LISTAR TAREFAS A FAZER
            4 - LISTAR TAREFAS SENDO FEITAS
            5 - LISTAR TAREFAS JA FEITAS
            6 - ATUALIZAR TAREFA
            7 - REMOVER TAREFA
            8 - ATUALIZAR STATUS TAREFA
            9 - CRIAR ALARME
            10 - ATIVAR ALARME 
            11 - DESATIVAR ALARME
            
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
                    menuTarefa.listarTarefasAFazer();
                    break;
                case 4:
                    menuTarefa.listarTarefasFazendo();
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
                case 8:
                    menuStatus.escolherTarefaParaAtualizarStatus(menuTarefa.listarTarefas());
                    break;
                case 9:
                    menuAlarme.escolherTarefaParaAlarme(menuTarefa.listarTarefas());
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

    private void dispararAlarmes() {
        try {
            List<Alarme> alarmes = alarmeController.listarAlarmes();

            if (!alarmes.isEmpty()) {
                System.out.println(alarmes);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
