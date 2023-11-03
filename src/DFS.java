import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DFS {
    Grafo grafo;
    @NotNull final Gson gson;
    @NotNull final ChamoAPI API;
    @NotNull HashSet<Integer> visitados;

    public DFS() {
        visitados = new HashSet<>();
        gson = new Gson();
        API = new ChamoAPI();
    }

    final public Grafo inicio() throws IOException, InterruptedException {

        String json = API.inicio().orElse("");
        Node node = gson.fromJson(json, Node.class);
        grafo = new Grafo(node);

        dfs(node.posAtual(), node.vizinhos());

        return this.grafo;
    }

    // TODO: Existe um corner case em que, caso um nó não
    //  tenha vizinhos restantes ele não entrará no loop,
    //  apenas retornará, sem mover de volta a posição
    //  original, causando um Movimento inválido.
    //
    // FIXED, acho
    private void dfs(final int raiz, final @NotNull ArrayList<Integer> vizinhos) throws IOException, InterruptedException {
        visitados.add(raiz);

        for (var item : vizinhos) {
            if (visitados.add(item)) {
                String json = API.proxMovimento(item);
                Node node = gson.fromJson(json, Node.class);

                grafo.putNode(node);

                dfs(node.posAtual(), node.vizinhos());
                //noinspection ResultOfMethodCallIgnored
                API.proxMovimento(raiz);
            }
        }

    }
}

