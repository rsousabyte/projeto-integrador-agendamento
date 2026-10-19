package Cadastro;

import java.util.Scanner;

public class Login {
    public static String executar(Scanner scanner) {
        System.out.println("|----------Login de usuário----------|");

        String email = "";
        String senha = "";
        boolean confirmado = false;

        while (!confirmado) {
            System.out.println("\nDigite o email: ");
            email = scanner.nextLine().trim().toLowerCase();

            System.out.println("Digite a senha: ");
            senha = scanner.nextLine();

            System.out.println("Email informado: " + email);

            while (true) {
                System.out.println("Confirmar? (Sim ou não):");
                String confirmacao = scanner.nextLine().trim();

                if (confirmacao.equalsIgnoreCase("Sim")) {
                    confirmado = true;
                    break;
                } else if (confirmacao.equalsIgnoreCase("Não") || confirmacao.equalsIgnoreCase("Nao")) {
                    System.out.println("Cancelado. Digite os dados novamente.");
                    break;
                } else {
                    System.out.println("Resposta inválida. Digite Sim ou não.");
                }
            }
        }

        

        int posicao = Dados.emails.indexOf(email);

        if (posicao != -1 && Dados.senhas.get(posicao).equals(senha)) {
            System.out.println("Login realizado!");
            return email;
        } else {
            System.out.println("Email ou senha incorretos.");
            return null;
        }
        
    }
}