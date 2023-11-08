import com.google.gson.Gson;
import comunicacao.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DFS {
    @NotNull
    final ChamoAPI API;
    @NotNull
    final HashSet<Integer> visitados;
    @NotNull
    final Grafo grafo;

    public DFS() {
        visitados = new HashSet<>();
        API = new ChamoAPI();
        grafo = new Grafo();
    }

    final public Grafo inicio() throws IOException, InterruptedException {
        Node node = API.inicio();

        grafo.putNode(node);
        dfs(node.posAtual(), node.vizinhos());

        return this.grafo;
    }

    private void dfs(final @NotNull Integer raiz,
                     final @NotNull ArrayList<Integer> vizinhos) throws IOException, InterruptedException {

        visitados.add(raiz);

        for (var item : vizinhos) {
            if (visitados.add(item)) {
                Node node = API.proxMovimento(item);

                grafo.putNode(node);

                dfs(node.posAtual(), node.vizinhos());
                //noinspection ResultOfMethodCallIgnored
                API.proxMovimento(raiz);
            }
        }

    }

}

