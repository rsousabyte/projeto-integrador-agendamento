package Cadastro;

public class Senhaforte {
    public static boolean senhaForte(String senha) {
        if (senha.length() < 8) {
            return false;
        }

        boolean temLetra = false;
        boolean temNumero = false;
        boolean temSimbolo = false;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);

            if (Character.isLetter(c)) {
                temLetra = true;
            } else if (Character.isDigit(c)) {
                temNumero = true;
            } else {
                temSimbolo = true;
            }
        }

        return temLetra && temNumero && temSimbolo;
    }
}
