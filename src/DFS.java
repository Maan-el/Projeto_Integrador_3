import comunicacao.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class DFS {
    @NotNull
    private final ChamoAPI API;
    @NotNull
    private final HashSet<Integer> visitados;
    private Grafo grafo;

    public DFS() {
        visitados = new HashSet<>();
        API = new ChamoAPI();
    }

    public final Grafo getGrafo() {
        return grafo;
    }


    final public @NotNull ArrayList<Integer> inicio() throws IOException, InterruptedException {
        Node node = API.inicio();

        grafo = getNewGrafo(node);

        foiVisitado(node.posAtual());

        return getCaminho(node);
    }

    @NotNull
    private ArrayList<Integer> getCaminho(@NotNull Node node) throws IOException, InterruptedException {
        final var caminhoInverso = dfs(node.posAtual(), node.vizinhos()).orElse(null);

        assert caminhoInverso != null;

        return fixCaminho(caminhoInverso);
    }

    @NotNull
    private ArrayList<Integer> fixCaminho(@NotNull ArrayList<Integer> caminhoReverso) {
        return new ArrayList<>(caminhoReverso.reversed());
    }

    @Contract("_, _ -> new")
    @NotNull
    private Optional<ArrayList<Integer>> finalDoCaminho(@NotNull Node node, @NotNull Integer raiz) {
        return Optional.of(new ArrayList<>(List.of(node.posAtual(), raiz)));
    }

    @Contract("_ -> new")
    @NotNull
    private Grafo getNewGrafo(@NotNull Node node) {
        Grafo grafo = new Grafo(node.posAtual(), new HashSet<>(), new HashMap<>());
        grafo.add(node);
        return grafo;
    }

    private boolean foiVisitado(@NotNull Integer node) {
        return !visitados.add(node);
    }

    private Optional<ArrayList<Integer>> dfs(@NotNull final Integer raiz,
                                             @NotNull final ArrayList<Integer> vizinhos) throws IOException, InterruptedException {

        for (var item : vizinhos) {
            Optional<ArrayList<Integer>> lista = getIntegers(raiz, item);
            if (lista.isPresent()) return lista;
        }
        return Optional.empty();
    }

    // TODO Arrumar um nome decente para essa função
    private Optional<ArrayList<Integer>> getIntegers(@NotNull Integer raiz, @NotNull Integer item) throws IOException, InterruptedException {
        if (foiVisitado(item)) {
            return Optional.empty();
        }

        Node node = API.movePara(item);

        grafo.add(node);

        if (node.fim()) return finalDoCaminho(node, raiz);

        var caminho = dfs(node.posAtual(), node.vizinhos());

        caminho.map((xs) -> xs.add(raiz));

        if (caminho.isEmpty()) {
            //noinspection ResultOfMethodCallIgnored
            API.movePara(raiz);
        }

        return caminho;
    }
}

