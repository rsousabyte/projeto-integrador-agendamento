package Usuarios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import Cadastro.Dados;

public class Fornecedor {
    public static void executar(Scanner scanner, String emailFornecedor) throws Exception {
        boolean agendado = false;

        while (!agendado) {
            String[] carga = telaCadastroCarga(scanner);
            agendado = telaAgendamento(scanner, emailFornecedor, carga);
        }
        System.out.println("\nObrigado! Você já pode sair do sistema.");
    }

    public static String[] telaCadastroCarga(Scanner scanner) {
        System.out.println("\n|----------Cadastro de carga----------|");
        System.out.println("Preencha as informações: ");

        int qtd = 0;
        boolean valido = false;
        while (!valido) {
            System.out.println("Digite a quantidade de produtos: ");
            String produtos = scanner.nextLine().trim();

            try {
                qtd = Integer.parseInt(produtos);
                if (qtd > 0) {
                    valido = true;
                } else {
                    System.out.println("A quantidade deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite apenas números.");
            }
        }

        String tiposProdutos;
        while (true) {
            System.out.println("Digite os tipos de produtos:");
            tiposProdutos = scanner.nextLine().trim();

            if (tiposProdutos.matches("[A-Za-zÀ-ÿ0-9 ]+")) {
                break;
            }
            System.out.println("Não use símbolos! Digite só letras e números.");
        }

        String placa;
        while (true) {
            System.out.println("Digite a placa do caminhão: ");
            placa = scanner.nextLine().trim().toUpperCase();

            if (placa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
                break;
            }
            System.out.println("Placa inválida! Use o formato ABC1D23 ou ABC1234.");
        }

        System.out.println("\nPressione ENTER para ir ao próximo passo (Próximo)...");
        scanner.nextLine();

        return new String[] { String.valueOf(qtd), tiposProdutos, placa };
    }

    public static boolean telaAgendamento(Scanner scanner, String emailFornecedor, String[] carga)
            throws Exception {

        System.out.println("\n|----------Agendamento----------|");
        System.out.println("Selecione o dia e horário para descarregamento");

        while (true) {
            String dia = escolherDia(scanner);
            if (dia == null) {
                return false; 
            }

            // ----- Tabela de horários -----
            String horario = escolherHorario(scanner, dia);
            if (horario == null) {
                return false; 
            }

            System.out.println("\nResumo do agendamento:");
            System.out.println("Quantidade: " + carga[0]);
            System.out.println("Tipos: " + carga[1]);
            System.out.println("Placa: " + carga[2]);
            System.out.println("Dia: " + dia + " às " + horario);

            if (confirmar(scanner, "Deseja confirmar o agendamento?")) {
                Dados.registrarAgendamento(emailFornecedor, carga[0], carga[1], carga[2],
                        dia, horario, "Não iniciou trajeto");

                System.out.println("Agendamento confirmado com sucesso!");
                return true;
            }
            System.out.println("Agendamento não confirmado. Escolha o dia e o horário novamente.");
        }
    }

    public static String escolherDia(Scanner scanner) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int quantidadeDeDias = 5; // para mudar quantos dias aparecem mexa só aqui

        while (true) {
            System.out.println("\nSelecione o dia para descarregamento:");
            for (int i = 1; i <= quantidadeDeDias; i++) {
                LocalDate dia = LocalDate.now().plusDays(i); 
                System.out.println(i + " - " + dia.format(formato));
            }
            System.out.println("0 - Voltar");

            String texto = scanner.nextLine().trim();
            int escolha;

            try {
                escolha = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite apenas números.");
                continue;
            }

            if (escolha == 0) {
                return null;
            }

            if (escolha < 1 || escolha > quantidadeDeDias) {
                System.out.println("Opção inválida. Selecione de 0-1.");
                continue;
            }

            return LocalDate.now().plusDays(escolha).format(formato);
        }
    }

    public static boolean confirmar(Scanner scanner, String pergunta) {
        while (true) {
            System.out.println(pergunta + " (Sim ou Não)");
            String resposta = scanner.nextLine().trim();

            if (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("s")) {
                return true;
            } else if (resposta.equalsIgnoreCase("Não") || resposta.equalsIgnoreCase("Nao")
                    || resposta.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Resposta inválida. Digite Sim ou Não.");
        }
    }

    public static String escolherHorario(Scanner scanner, String diaEscolhido) {

        //Para mudar os horários, é só mexer aqui.
        String[] horariosDisponiveis = { "08:00", "10:00", "14:00", "16:00" };

        while (true) {
            System.out.println("Selecione o horário para descarregamento:");

            for (int i = 0; i < horariosDisponiveis.length; i++) {
                String horario = horariosDisponiveis[i];

                if (horarioOcupado(diaEscolhido, horario)) {
                    System.out.println((i + 1) + " - " + horario + " (ocupado)");
                } else {
                    System.out.println((i + 1) + " - " + horario);
                }
            }
            System.out.println("0 - Voltar");

            String texto = scanner.nextLine().trim();
            int escolha;

            try {
                escolha = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite apenas números.");
                continue; 
            }

            if (escolha == 0) {
                return null; 
            }

            if (escolha < 1 || escolha > horariosDisponiveis.length) {
                System.out.println("Opção inválida.");
                continue;
            }

            String horarioEscolhido = horariosDisponiveis[escolha - 1];

            if (horarioOcupado(diaEscolhido, horarioEscolhido)) {
                System.out.println("Esse horário já está ocupado. Escolha outro.");
                continue;
            }

            return horarioEscolhido; 
        }
    }

    // Percorre os agendamentos já salvos e verifica se o dia + horário já foram usados
    public static boolean horarioOcupado(String dia, String horario) {
        for (int i = 0; i < Dados.dias.size(); i++) {
            if (Dados.dias.get(i).equals(dia) && Dados.horarios.get(i).equals(horario)) {
                return true; 
            }
        }
        return false;
    }
}
