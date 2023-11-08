import comunicacao.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DFS {
    Grafo grafo;
    @NotNull
    final ChamoAPI API;
    @NotNull
    final HashSet<Integer> visitados;

    public DFS() {
        visitados = new HashSet<>();
        API = new ChamoAPI();
    }

    final public Grafo inicio() throws IOException, InterruptedException {

        Node node = API.inicio().transform(API::toNode);

        grafo = new Grafo(node);

        dfs(node.posAtual(), node.vizinhos());

        return this.grafo;
    }

    private void dfs(final int raiz, final @NotNull ArrayList<Integer> vizinhos) throws IOException, InterruptedException {
        visitados.add(raiz);

        for (var item : vizinhos) {
            if (visitados.add(item)) {
                Node node = API.proxMovimento(item).transform(API::toNode);

                grafo.putNode(node);

                dfs(node.posAtual(), node.vizinhos());
                //noinspection ResultOfMethodCallIgnored
                API.proxMovimento(raiz);
            }
        }

    }
}

