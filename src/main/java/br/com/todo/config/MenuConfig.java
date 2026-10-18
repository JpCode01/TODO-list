package br.com.todo.config;

import br.com.todo.validation.ValidationAlarme;
import br.com.todo.validation.ValidationTarefa;
import br.com.todo.view.MenuAlarme;
import br.com.todo.view.MenuStatus;
import br.com.todo.view.MenuTarefa;

import java.util.Scanner;

public class MenuConfig {

    public final MenuAlarme menuAlarme;
    public final MenuStatus menuStatus;
    public final MenuTarefa menuTarefa;

    public MenuConfig(
            Scanner scanner,
            ControllerConfig controllerConfig,
            ValidationAlarme validationAlarme
    ) {

        menuTarefa = new MenuTarefa(
                scanner,
                controllerConfig.tarefaController,
                new ValidationTarefa(),
                controllerConfig.statusController
        );

        menuStatus = new MenuStatus(
                scanner,
                controllerConfig.tarefaController,
                controllerConfig.statusController
        );

        menuAlarme = new MenuAlarme(
                scanner,
                controllerConfig.alarmeController,
                controllerConfig.tarefaController,
                validationAlarme
        );
    }
}