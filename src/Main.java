import comunicacao.CaminhoValidado;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final API api = new API();
        final DFS dfs = new DFS(api);

        ArrayList<Integer> movimentos = null;
        CaminhoValidado caminhoValidado = null;

        final long inicio = System.nanoTime();

        try {
            movimentos = dfs.inicio();
            caminhoValidado = api.validaCaminho(movimentos);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(e.hashCode());
        }

        System.out.println(caminhoValidado.caminho_valido() + " " + caminhoValidado.quantidade_movimentos());

        final long fim = System.nanoTime();
        final long ms = (fim - inicio) / 1_000_000;

        System.out.println(movimentos);

        System.out.println("Execução = " + ms + "ms");
    }
}