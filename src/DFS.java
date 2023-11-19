import com.google.gson.Gson;
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
    private final Gson gson = new Gson();

    public DFS(@NotNull final ChamoAPI api) {
        API = api;
        visitados = new HashSet<>();
    }

    final public @NotNull ArrayList<Integer> inicio() throws IOException, InterruptedException {
        Node node = API.inicio().map(this::toNode).get();

        visitados.add(node.posAtual());

        return getCaminho(node);
    }

    @NotNull
    private ArrayList<Integer> getCaminho(@NotNull Node node) throws IOException, InterruptedException {
        final var caminhoInverso = dfs(node.posAtual(), node.vizinhos()).get();

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
        if (!visitados.add(item)) {
            return Optional.empty();
        }

        String json = API.proxMovimento(item);

        Node node = toNode(json);

        if (node.fim()) return finalDoCaminho(node, raiz);

        var caminho = dfs(node.posAtual(), node.vizinhos());

        caminho.map((xs) -> xs.add(raiz));

        if (caminho.isEmpty()) {
            //noinspection ResultOfMethodCallIgnored
            API.proxMovimento(raiz);
        }

        return caminho;
    }

    private Node toNode(@NotNull String json) {
        return gson.fromJson(json, Node.class);
    }
}

