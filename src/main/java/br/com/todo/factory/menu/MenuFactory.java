package br.com.todo.factory.menu;

import br.com.todo.config.ControllerConfig;
import br.com.todo.config.DaoConfig;
import br.com.todo.config.MenuConfig;
import br.com.todo.config.ServiceConfig;
import br.com.todo.validation.ValidationAlarme;
import br.com.todo.validation.ValidationTarefa;

import java.util.Scanner;

public class MenuFactory {

    private final DaoConfig daoConfig;
    private final ServiceConfig serviceConfig;
    private final ControllerConfig controllerConfig;
    private final MenuConfig menuConfig;

    public MenuFactory(Scanner scanner) {
        daoConfig = new DaoConfig();

        serviceConfig = new ServiceConfig(
                daoConfig
        );

        controllerConfig = new ControllerConfig(
                serviceConfig
        );

        menuConfig = new MenuConfig(
                scanner,
                controllerConfig,
                new ValidationAlarme()
        );
    }

    public MenuConfig getMenuConfig() {
        return menuConfig;
    }

    public ControllerConfig getControllerConfig() {
        return controllerConfig;
    }
}