import java.util.Scanner;

import Cadastro.Cadastro;
import Cadastro.Dados;
import Cadastro.Login;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Dados.carregar();

        System.out.println("|----------Cadastro de usuário----------|");
        boolean logado = false;

        while (!logado) {
            System.out.print("\nPossui cadastro? (Sim/Não): ");
            String possui = scanner.nextLine();
            if (possui.equalsIgnoreCase("Sim")) {
                logado = Login.executar(scanner) != null;
            } else if (possui.toLowerCase().startsWith("n") || possui.equalsIgnoreCase("Não") || possui.equalsIgnoreCase("Nao")) {
                Cadastro.executar(scanner);
            } else {
                System.out.println("Resposta inválida. Digite Sim ou Não.");
            }
        }
        System.out.println("Bem-vindo ao sistema!");
        scanner.close();
    }
}
