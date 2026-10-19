package br.com.todo.view;

import java.util.Scanner;

public class EntradaConsole {

    private final Scanner scanner;

    public EntradaConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public long solicitarId(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            String idInput = scanner.nextLine();

            try {
                return Long.parseLong(idInput);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido! Digite um número inteiro.");
            }
        }
    }
}
