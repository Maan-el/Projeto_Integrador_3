import comunicacao.CaminhoValidado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ChamoAPI api = new ChamoAPI();

        System.out.println("Insira nome do labirinto a ser explorado");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String nomeLabirinto = bufferedReader.readLine();
        api.setNomeLabirinto(nomeLabirinto);

        DFS dfs = new DFS(api);

        ArrayList<Integer> movimentos = null;

        System.out.println("Acessando " + api.getLabirinto());
        final long inicio = System.nanoTime();

        CaminhoValidado caminhoValidado = null;

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