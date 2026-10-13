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

    public static void salvar(String nome, String email, String senha, String tipo) throws Exception {
        FileWriter arquivo = new FileWriter("usuarios.txt", true);
        arquivo.write(nome + ";" + email + ";" + senha + ";" + tipo + "\n");
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

                nomes.add(partes[0]);
                emails.add(partes[1]);
                senhas.add(partes[2]);
                tipos.add(partes[3]);
            }

            leitor.close();
        }
    }
}
