// Todas as classes que estão sendo usadas somente para pegar valores de uma API foram substituidas por Records,
// pois elas possuiam apenas getters e todos os seus valores eram constantes,
// por isso Record declara intenção melhor que Class.

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        DFS dfs = new DFS();

        ChamoAPI api = new ChamoAPI();
        System.out.println(api.getNomes());
        Grafo grafo = dfs.inicio();

        System.out.println(grafo.getGrafo().entrySet());
        System.out.println(grafo.getSaidas());
    }
}