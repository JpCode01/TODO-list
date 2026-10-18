package br.com.todo.validation;

import br.com.todo.exception.prioridade.PrioridadeIncorretaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ValidationTarefa {
    public LocalDate validarData(String dataInput, Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                return LocalDate.parse(dataInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Digite uma data no formato dd/MM/yyyy.");
                dataInput = scanner.nextLine();
            }
        }
    }

    public int validarPrioridade(String prioridadeInput, Scanner scanner) {
        while (true) {
            try {
                int prioridade = Integer.parseInt(prioridadeInput);
                if (prioridade < 1 || prioridade > 5) {
                    throw new PrioridadeIncorretaException(prioridade);
                }
                return prioridade;
            } catch (NumberFormatException e) {
                System.out.println("Prioridade inválida! Digite um número inteiro.");
                prioridadeInput = scanner.nextLine();
            } catch (PrioridadeIncorretaException e) {
                System.out.println(e.getMessage());
                prioridadeInput = scanner.nextLine();
            }
        }
    }
}
