package Cadastro;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Dados {
    public static ArrayList<String> tipos = new ArrayList<>();
    public static ArrayList<String> nomes = new ArrayList<>();
    public static ArrayList<String> emails = new ArrayList<>();
    public static ArrayList<String> senhas = new ArrayList<>();

    public static ArrayList<String> agEmails = new ArrayList<>();
    public static ArrayList<String> qtd = new ArrayList<>();
    public static ArrayList<String> tiposprodutos = new ArrayList<>();
    public static ArrayList<String> placas = new ArrayList<>();
    public static ArrayList<String> dias = new ArrayList<>();
    public static ArrayList<String> horarios = new ArrayList<>();
    public static ArrayList<String> statusAg = new ArrayList<>();

    public static ArrayList<String> horarioChegadas = new ArrayList<>();
    public static ArrayList<String> horarioSaidas = new ArrayList<>();
    public static int funcionariosPorEquipe = 0;
    public static int docasDisponiveis = 0;

    private static String valor(String parte) {
        int posicao = parte.indexOf(": ");

        if (posicao == -1) {
            return parte.trim();
        }

        return parte.substring(posicao + 2).trim();
    }

    // usuarios
    public static void salvar(String nome, String email, String senha, String tipo) throws Exception {
        FileWriter arquivo = new FileWriter("usuarios.txt", true);
        arquivo.write("Nome: " + nome + " ;" + "E-mail: " + email + " ;" + "Senha: " + senha + " ;" + "Tipo: "
                + tipo + "\n");
        arquivo.close();
    }

    public static void carregar() throws Exception {
        File arquivo = new File("usuarios.txt");

        if (arquivo.exists()) {
            Scanner leitor = new Scanner(arquivo);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (linha.isBlank()) {
                    continue;
                }

                String[] partes = linha.split(";");

                if (partes.length < 4) {
                    continue;
                }

                nomes.add(valor(partes[0]));
                emails.add(valor(partes[1]));
                senhas.add(valor(partes[2]));
                tipos.add(valor(partes[3]));
            }

            leitor.close();
        }
    }

    // agendamentos
    public static void salvarAgendamento(String emailAgendamento, String quantidade, String tipoDeProduto,
            String placa, String dia, String horario, String status) throws Exception {
        FileWriter arquivoAgendamento = new FileWriter("agendamentos.txt", true);
        arquivoAgendamento.write("E-mail do agendamento: " + emailAgendamento + " ;" + "Quantidade: " + quantidade
                + " ;" + "Tipo de produto: " + tipoDeProduto + " ;" + "Placa do veículo: " + placa + " ;"
                + "Dia: " + dia + " ;" + "Horário: " + horario + " ;" + "Status: " + status + "\n");
        arquivoAgendamento.close();
    }

    public static void registrarAgendamento(String email, String quantidade, String tipoDeProduto,
            String placa, String dia, String horario, String status) throws Exception {
        agEmails.add(email);
        qtd.add(quantidade);
        tiposprodutos.add(tipoDeProduto);
        placas.add(placa);
        dias.add(dia);
        horarios.add(horario);
        statusAg.add(status);

        horarioChegadas.add("-");
        horarioSaidas.add("-");

        salvarAgendamento(email, quantidade, tipoDeProduto, placa, dia, horario, status);
        salvarHorarios();
    }

    public static void carregarAgendamento() throws Exception {
        File arquivo = new File("agendamentos.txt");

        if (arquivo.exists()) {
            Scanner leitor = new Scanner(arquivo);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (linha.isBlank()) {
                    continue;
                }

                String[] partes = linha.split(";");

                if (partes.length < 7) {
                    continue;
                }

                agEmails.add(valor(partes[0]));
                qtd.add(valor(partes[1]));
                tiposprodutos.add(valor(partes[2]));
                placas.add(valor(partes[3]));
                dias.add(valor(partes[4]));
                horarios.add(valor(partes[5]));
                statusAg.add(valor(partes[6]));
            }

            leitor.close();
        }
    }

    // chegada e saida
    public static void salvarHorarios() throws Exception {
        FileWriter arquivo = new FileWriter("chegadas_saidas.txt");
        for (int i = 0; i < horarioChegadas.size(); i++) {
            arquivo.write("Horário das chegadas: " + horarioChegadas.get(i) + " ;" + "Horário das saídas: "
                    + horarioSaidas.get(i) + "\n");
        }
        arquivo.close();
    }

    public static void registrarHorarios(int posicao, String chegada, String saida) throws Exception {
        horarioChegadas.set(posicao, chegada);
        horarioSaidas.set(posicao, saida);
        salvarHorarios();
    }

    public static void carregarHorarios() throws Exception {
        File arquivo = new File("chegadas_saidas.txt");

        if (arquivo.exists()) {
            Scanner leitor = new Scanner(arquivo);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (linha.isBlank()) {
                    continue;
                }

                String[] partes = linha.split(";");

                if (partes.length < 2) {
                    horarioChegadas.add("-");
                    horarioSaidas.add("-");
                    continue;
                }

                horarioChegadas.add(valor(partes[0]));
                horarioSaidas.add(valor(partes[1]));
            }

            leitor.close();
        }

        while (horarioChegadas.size() < agEmails.size()) {
            horarioChegadas.add("-");
            horarioSaidas.add("-");
        }
        while (horarioChegadas.size() > agEmails.size()) {
            horarioChegadas.remove(horarioChegadas.size() - 1);
            horarioSaidas.remove(horarioSaidas.size() - 1);
        }
    }

    // time de operacao
    public static void registrarOperacao(int funcionarios, int docas) throws Exception {
        funcionariosPorEquipe = funcionarios;
        docasDisponiveis = docas;

        FileWriter arquivo = new FileWriter("operacao.txt"); // reescreve: guarda só o valor mais recente
        arquivo.write("Funcionários: " + funcionarios + " ;" + "Docas: " + docas + "\n");
        arquivo.close();
    }

    public static void carregarOperacao() throws Exception {
        File arquivo = new File("operacao.txt");

        if (arquivo.exists()) {
            Scanner leitor = new Scanner(arquivo);

            if (leitor.hasNextLine()) {
                String[] partes = leitor.nextLine().split(";");

                if (partes.length >= 2) {
                    try {
                        funcionariosPorEquipe = Integer.parseInt(valor(partes[0]));
                        docasDisponiveis = Integer.parseInt(valor(partes[1]));
                    } catch (NumberFormatException e) {
                        // arquivo estragado: mantém os valores 0
                    }
                }
            }

            leitor.close();
        }
    }
}
