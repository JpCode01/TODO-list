package br.com.todo.validation;

import br.com.todo.exception.DiasNegativosException;

import java.util.Scanner;

public class ValidationAlarme {
    
    public int validarDias(String inputDias, Scanner scanner) {
        while (true) {
            try {
                int dias = Integer.parseInt(inputDias);
                if (dias < 0) {
                    throw new DiasNegativosException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Prioridade inválida! Digite um número inteiro.");
                inputDias = scanner.nextLine();
            }
        }
    }
}
