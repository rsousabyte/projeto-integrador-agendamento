package Usuarios;

import java.util.Scanner;
import Cadastro.Dados;

public class TimeOperacao {
    public static void executar(Scanner scanner) throws Exception {
        System.out.println("\n|----------Time de Operação----------|");
        System.out.println("Preencha as informações: ");

        int funcionarios = lerNumeroPositivo(scanner, "Digite o n° de funcionários por equipe: ");
        int docas = lerNumeroPositivo(scanner, "Digite o n° de docas disponíveis: ");
        Dados.registrarOperacao(funcionarios, docas);
        System.out.println("Registro da operação salvo!");

        mostrarRegistrosFornecedores();
        registrarChegadaSaida(scanner);
        System.out.println("\nRegistros da operação finalizados.");
    }

    public static int lerNumeroPositivo(Scanner scanner, String pergunta) {
        while (true) {
            System.out.println(pergunta);
            String texto = scanner.nextLine().trim();

            try {
                int numero = Integer.parseInt(texto);

                if (numero > 0) {
                    return numero;
                }
                System.out.println("A quantidade deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite apenas números.");
            }
        }
    }

    public static void mostrarRegistrosFornecedores() {
        System.out.println("\n|----------Registros dos fornecedores----------|");

        if (Dados.agEmails.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado ainda.");
            return;
        }

        for (int i = 0; i < Dados.agEmails.size(); i++) {

            String email = Dados.agEmails.get(i);
            int posicaoUsuario = Dados.emails.indexOf(email);
            String nome = email;
            if (posicaoUsuario != -1) {
                nome = Dados.nomes.get(posicaoUsuario);
            }

            System.out.println("\n" + (i + 1) + ") Fornecedor: " + nome);
            System.out.println("   Placa: " + Dados.placas.get(i));
            System.out.println("   Dia/horário: " + Dados.dias.get(i) + " às " + Dados.horarios.get(i));
            System.out.println("   Quantidade: " + Dados.qtd.get(i) + " | Tipos: " + Dados.tiposprodutos.get(i));
            System.out.println("   Status: " + Dados.statusAg.get(i));
            System.out.println("   Chegada: " + Dados.horarioChegadas.get(i)
                    + " | Saída: " + Dados.horarioSaidas.get(i));
        }
    }

    public static void registrarChegadaSaida(Scanner scanner) throws Exception {
        if (Dados.agEmails.isEmpty()) {
            return;
        }

        while (true) {
            System.out.println("\nDigite o número do agendamento para registrar chegada e saída (0 para finalizar): ");
            String texto = scanner.nextLine().trim();
            int escolha;

            try {
                escolha = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite apenas números.");
                continue;
            }

            if (escolha == 0) {
                return;
            }

            if (escolha < 1 || escolha > Dados.agEmails.size()) {
                System.out.println("Opção inválida. Digite um número de 0 a " + Dados.agEmails.size() + ".");
                continue;
            }

            int posicao = escolha - 1;

            if (!Dados.horarioChegadas.get(posicao).equals("-")) {
                System.out.println("Esse caminhão já tem chegada " + Dados.horarioChegadas.get(posicao)
                        + " e saída " + Dados.horarioSaidas.get(posicao) + ".");

                if (!Fornecedor.confirmar(scanner, "Deseja substituir?")) {
                    continue;
                }
            }

            String chegada = lerHorario(scanner, "Digite o horário de chegada (HH:mm): ");

            String saida;
            while (true) {
                saida = lerHorario(scanner, "Digite o horário de saída (HH:mm): ");

                if (saida.compareTo(chegada) > 0) {
                    break;
                }
                System.out.println("A saída deve ser depois da chegada (" + chegada + ").");
            }

            Dados.registrarHorarios(posicao, chegada, saida);
            System.out.println("Horários registrados!");
        }
    }

    public static String lerHorario(Scanner scanner, String pergunta) {
        while (true) {
            System.out.println(pergunta);
            String horario = scanner.nextLine().trim();

            if (horario.matches("([01][0-9]|2[0-3]):[0-5][0-9]")) {
                return horario;
            }
            System.out.println("Horário inválido! Use o formato HH:mm, por exemplo 08:30.");
        }
    }
}
