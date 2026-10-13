package Cadastro;

import java.util.Scanner;

public class Login {
    public static boolean executar(Scanner sc) {
        System.out.println("|----------Login de usuário----------|");
        System.out.println("\nDigite o email: ");
        String email = sc.nextLine().trim().toLowerCase();

        System.out.println("Digite a senha: ");
        String senha = sc.nextLine();
        int posicao = Dados.emails.indexOf(email);

        if (posicao != -1 && Dados.senhas.get(posicao).equals(senha)) {
            System.out.println("Login realizado!");
            return true;
        } else {
            System.out.println("Email ou senha incorretos.");
            return false;
        }
    }

}
