import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final long comeco = System.nanoTime();

        DFS dfs = new DFS();

        ChamoAPI api = new ChamoAPI();
        ArrayList<Integer> movimentos = null;

        try {
            System.out.println(api.getNomes());
            movimentos = dfs.inicio();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(e.hashCode());
        }

        System.out.println(movimentos);

        try {
            api.fim(movimentos);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(
                dfs.getGrafo()
                        .listaAdjacencia()
                        .entrySet());

        final long fim = System.nanoTime();
        final long ms = (fim - comeco) / 1_000_000;

        System.out.println("Execução = " + ms + "ms");
    }

}