import java.util.Scanner;

import Cadastro.Cadastro;
import Cadastro.Dados;
import Cadastro.Login;
import Usuarios.Fornecedor;
import Usuarios.TimeOperacao;

public class Main {
    public static void main(String[] args) throws Exception {

        String codificacao = System.getProperty("stdin.encoding", "Cp850");
        Scanner scanner = new Scanner(System.in, codificacao);
        Dados.carregar();
        Dados.carregarAgendamento();
        Dados.carregarHorarios();
        Dados.carregarOperacao();

        System.out.println("|----------Tela de Login----------|");

        String emailLogado = null;

        while (emailLogado == null) {
            System.out.print("\nPossui cadastro? (Sim ou Não): ");
            String possui = scanner.nextLine().trim();

            if (possui.equalsIgnoreCase("Sim")) {
                emailLogado = Login.executar(scanner);
            } else if (possui.toLowerCase().startsWith("n") || possui.equalsIgnoreCase("Não")
                    || possui.equalsIgnoreCase("Nao")) {
                emailLogado = Cadastro.executar(scanner);
            } else {
                System.out.println("Resposta inválida. Digite Sim ou Não.");
            }
        }
        System.out.println("Bem-vindo ao sistema!");

        int posicao = Dados.emails.indexOf(emailLogado);
        String tipo = Dados.tipos.get(posicao);

        abrirTela(tipo, emailLogado, scanner);

        scanner.close();
    }

    public static void abrirTela(String tipo, String email, Scanner scanner) throws Exception {
        if (tipo.equals("4")) {
            Fornecedor.executar(scanner, email);
        } else if (tipo.equals("3")) {
            System.out.println("Tela do Time de Planejamento em construção.");
        } else if (tipo.equals("2")) {
            TimeOperacao.executar(scanner);
        } else if (tipo.equals("1")) {
            System.out.println("Tela do Administrador em construção.");
        }
    }
}
