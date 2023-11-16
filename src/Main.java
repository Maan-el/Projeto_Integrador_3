import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ChamoAPI api = new ChamoAPI();

        DFS dfs = new DFS(api);


        ArrayList<Integer> movimentos = null;

        final long inicio = System.nanoTime();

        try {
//            System.out.println(api.getNomes());
            movimentos = dfs.inicio();
            api.fim(movimentos);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(e.hashCode());
        }

        System.out.println(movimentos);
        System.out.println(dfs.getGrafo().listaAdjacencia().entrySet() + " " + dfs.getGrafo().saidas());

        final long fim = System.nanoTime();
        final long ms = (fim - inicio) / 1_000_000;

        System.out.println("Execução = " + ms + "ms");
    }
}