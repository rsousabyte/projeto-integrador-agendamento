package Cadastro;

import java.util.Scanner;

public class Cadastro {
    public static void executar(Scanner scanner) throws Exception {
        // Cadastro se não possui

        System.out.println("Digite o Nome e Sobrenome: ");
        String nome = scanner.nextLine();
        while (nome.matches(".*[0-9].*")) {
            System.out.println("Nome inválido. Use apenas letras e espaços: ");
            nome = scanner.nextLine();
        }

        System.out.println("Digite o email: ");
        String email = scanner.nextLine().trim().toLowerCase();
        while (!email.matches("[^@ ]+@[^@ ]+\\.[^@ ]+") || Dados.emails.contains(email)) {
            System.out.println("Email inválido ou já cadastrado. Digite novamente: ");
            email = scanner.nextLine().trim().toLowerCase();
        }

        System.out.println("Digite a senha (mínimo 8 caracteres, com letras, números e símbolos): ");
        String senha = scanner.nextLine();

        while (!Senhaforte.senhaForte(senha)) {
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

        System.out.println(
                "Selecionar tipo de usuário\n 1 - Administrador\n 2 - Time de Operação\n 3 - Time de Planejamento\n 4 - Fornecedor ");
        String tipo = scanner.nextLine();

        while (!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3") && !tipo.equals("4")) {
            System.out.println("Opção inválida. Digite um número de 1 a 4: ");
            tipo = scanner.nextLine();
        }

        boolean senhaConfirmada = senha.equals(repetida);
        boolean nomeConfirmado = !nome.isEmpty();

        System.out.println("\n--- Confira seus dados ---");
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Tipo de usuário: " + tipo);

        System.out.println("Confirmar cadastro? (Sim/Não)");
        String resposta = scanner.nextLine();

        if (senhaConfirmada && nomeConfirmado && resposta.equalsIgnoreCase("Sim")) {

            Dados.nomes.add(nome);
            Dados.emails.add(email);
            Dados.senhas.add(senha);
            Dados.tipos.add(tipo);
            Dados.salvar(nome, email, senha, tipo);

            System.out.println("Cadastro realizado!");
        } else {
            System.out.println("Cadastro cancelado. Nada foi salvo.");
        }
    }
}
