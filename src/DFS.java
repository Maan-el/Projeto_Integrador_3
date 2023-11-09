import comunicacao.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class DFS {
    @NotNull
    final ChamoAPI API;
    @NotNull
    final HashSet<Integer> visitados;

    Grafo grafo;

    public DFS() {
        visitados = new HashSet<>();
        API = new ChamoAPI();
    }

    final public ArrayList<Integer> inicio() throws IOException, InterruptedException {
        Node node = API.inicio();

        grafo = new Grafo(node.posAtual(), new HashSet<>(), new HashMap<>());
        grafo.add(node);

        visitados.add(node.posAtual());
        final var caminhoReverso = dfs(node.posAtual(), node.vizinhos()).orElse(null);

        assert caminhoReverso != null;
        final var caminho = new ArrayList<>(caminhoReverso.reversed());

        caminho.remove(0);

        return caminho;
    }

    private Optional<ArrayList<Integer>> dfs(@NotNull final Integer raiz,
                                             @NotNull final ArrayList<Integer> vizinhos) throws IOException, InterruptedException {

        for (var item : vizinhos) {
            Optional<ArrayList<Integer>> node = getIntegers(raiz, item);
            if (node.isPresent()) return node;
        }
        return Optional.empty();
    }

    private Optional<ArrayList<Integer>> getIntegers(@NotNull Integer raiz, @NotNull Integer item) throws IOException, InterruptedException {
        if (visitados.add(item)) {
            Node node = API.proxMovimento(item);

            if (node.fim()) return Optional.of(new ArrayList<>(List.of(node.posAtual(), raiz)));

            Optional<ArrayList<Integer>> retorno = dfs(node.posAtual(), node.vizinhos());

            if (retorno.isPresent()) {
                retorno.get().add(raiz);
                return retorno;
            }

            //noinspection ResultOfMethodCallIgnored
            API.proxMovimento(raiz);
        }
        return Optional.empty();
    }

    public final Grafo getGrafo() {
        return grafo;
    }
}

