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

    /**
     * Constructor
     */
    public DFS() {
        visitados = new HashSet<>();
        API = new ChamoAPI();
    }

    /**
     * @param caminhoReverso
     * @return
     */
    @Contract("_ -> new")
    @NotNull
    private ArrayList<Integer> fixCaminho(@NotNull ArrayList<Integer> caminhoReverso) {
        return new ArrayList<>(caminhoReverso.reversed());
    }

    /**
     * @param node
     * @param raiz
     * @return
     */
    @Contract("_, _ -> new")
    @NotNull
    private Optional<ArrayList<Integer>> finalDoCaminho(@NotNull Node node, @NotNull Integer raiz) {
        return Optional.of(new ArrayList<>(List.of(node.posAtual(), raiz)));
    }

    /**
     * @return
     */
    @Contract(pure = true)
    @NotNull
    public final Grafo getGrafo() {
        return grafo;
    }

    /**
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @NotNull
    final public ArrayList<Integer> inicio() throws IOException, InterruptedException {
        Node node = API.inicio();

        grafo = getNewGrafo(node);

        foiVisitado(node.posAtual());

        final var caminhoInverso = dfs(node.posAtual(), node.vizinhos()).orElse(null);

        assert caminhoInverso != null;

        return fixCaminho(caminhoInverso);
    }

    @Contract("_ -> new")
    @NotNull
    private Grafo getNewGrafo(@NotNull Node node) {
        Grafo grafo = new Grafo(node.posAtual(), new HashSet<>(), new HashMap<>());
        grafo.add(node);
        return grafo;
    }

    /**
     * @param node
     * @return
     */
    private boolean foiVisitado(@NotNull Integer node) {
        return !visitados.add(node);
    }

    /**
     * @param raiz
     * @param vizinhos
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private Optional<ArrayList<Integer>> dfs(@NotNull final Integer raiz,
                                             @NotNull final ArrayList<Integer> vizinhos) throws IOException, InterruptedException {

        for (var item : vizinhos) {
            Optional<ArrayList<Integer>> node = getIntegers(raiz, item);
            if (node.isPresent()) return node;
        }
        return Optional.empty();
    }

    /**
     * @param raiz
     * @param item
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    // TODO Arrumar um nome decente para essa função
    private Optional<ArrayList<Integer>> getIntegers(@NotNull Integer raiz, @NotNull Integer item) throws IOException, InterruptedException {
        if (foiVisitado(item)) {
            return Optional.empty();
        }

        Node node = API.proxMovimento(item);

        grafo.add(node);

        if (node.fim()) return finalDoCaminho(node, raiz);

        var caminho = dfs(node.posAtual(), node.vizinhos());

        caminho.map((xs) -> xs.add(raiz));

        //noinspection ResultOfMethodCallIgnored
        if (caminho.isEmpty()) {
            API.proxMovimento(raiz);
        }

        return caminho;
    }
}

