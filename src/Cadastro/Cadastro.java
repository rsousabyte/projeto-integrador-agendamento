package Cadastro;

import java.util.Scanner;

public class Cadastro {
    public static String executar(Scanner scanner) throws Exception {
        // Cadastro se não possui

        System.out.println("Digite o Nome e Sobrenome: ");
        String nome = scanner.nextLine().trim();
        while (!nome.matches("[\\p{L} ]+")) {
            System.out.println("Nome inválido. Use apenas letras e espaços: ");
            nome = scanner.nextLine().trim();
        }

        System.out.println("Digite o email: ");
        String email = scanner.nextLine().trim().toLowerCase();
        while (!email.matches("[^@ ]+@[^@ ]+\\.[^@ ]+") || Dados.emails.contains(email)) {
            System.out.println("Email inválido ou já cadastrado. Digite novamente: ");
            email = scanner.nextLine().trim().toLowerCase();
        }

        System.out.println("Digite a senha (mínimo 8 caracteres, com letras, números e símbolos): ");
        String senha = scanner.nextLine();
        while (!Senhaforte.senhaForte(senha) || senha.contains(";")) {
            System.out
                    .println("Digite uma senha mais forte. Use 8 ou mais caracteres, com letras, números e símbolos: ");
            senha = scanner.nextLine();
        }

        System.out.println("Repita a senha: ");
        String repetida = scanner.nextLine();

        while (!senha.equals(repetida)) {
            System.out.println("As senhas não coincidem. Repita a senha: ");
            repetida = scanner.nextLine();
        }

        String tipo = "";
        boolean confirmado = false;

        while (!confirmado) {
            System.out.println(
                    "Selecionar tipo de usuário\n 1 - Administrador\n 2 - Time de Operação\n 3 - Time de Planejamento\n 4 - Fornecedor ");
            tipo = scanner.nextLine().trim();

            while (!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3") && !tipo.equals("4")) {
                System.out.println("Opção inválida. Digite um número de 1 a 4: ");
                tipo = scanner.nextLine().trim();
            }

            while (true) {
                System.out.println("Confirmar? (Sim ou não):");
                String confirmacao = scanner.nextLine().trim();

                if (confirmacao.equalsIgnoreCase("Sim")) {
                    System.out.println("Confirmado!");
                    confirmado = true;
                    break;
                } else if (confirmacao.equalsIgnoreCase("Não") || confirmacao.equalsIgnoreCase("Nao")) {
                    System.out.println("Cancelado. Escolha novamente: ");
                    break;
                } else {
                    System.out.println("Resposta inválida. Digite Sim ou não.");
                }
            }
        }

        boolean senhaConfirmada = senha.equals(repetida);
        boolean nomeConfirmado = !nome.isEmpty();

        System.out.println("\n--- Confira seus dados ---");
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Tipo de usuário: " + tipo);

        System.out.println("Confirmar cadastro? (Sim/Não)");
        String resposta = scanner.nextLine();

        boolean respondeuSim = resposta.trim().equalsIgnoreCase("Sim") || resposta.trim().equalsIgnoreCase("s");

        if (senhaConfirmada && nomeConfirmado && respondeuSim) {
            Dados.nomes.add(nome);
            Dados.emails.add(email);
            Dados.senhas.add(senha);
            Dados.tipos.add(tipo);
            Dados.salvar(nome, email, senha, tipo);

            System.out.println("Cadastro realizado!");
            return email; 
        } else {
            System.out.println("Cadastro cancelado. Nada foi salvo.");
            return null;
        }
    }
}
