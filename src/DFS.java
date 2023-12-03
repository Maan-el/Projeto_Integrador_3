import com.google.gson.Gson;
import comunicacao.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DFS {
    @NotNull
    private final ChamoAPI ChamoAPI;
    @NotNull
    private final HashSet<Integer> visitados;

    public DFS(@NotNull final ChamoAPI chamoApi) {
        this.ChamoAPI = chamoApi;
        this.visitados = new HashSet<>();
    }

    final public @NotNull ArrayList<Integer> inicio() throws IOException, InterruptedException {
        Node node = ChamoAPI.inicio().transform(node());

        visitados.add(node.posAtual());

        return dfs(node)
                .map(fixCaminho())
                .get();
    }

    @Contract(pure = true)
    private @NotNull Function<List<Integer>, ArrayList<Integer>> fixCaminho() {
        return (caminho) -> new ArrayList<>(caminho.reversed());
    }

    @Contract("_, _ -> new")
    @NotNull
    private Optional<ArrayList<Integer>> finalDoCaminho(@NotNull final Node node, @NotNull final Integer raiz) {
        return Optional.of(new ArrayList<>(List.of(node.posAtual(), raiz)));
    }

    private Optional<ArrayList<Integer>> dfs(@NotNull final Node raiz) throws IOException, InterruptedException {
        for (var item : raiz.vizinhos()) {
            Optional<ArrayList<Integer>> lista = getIntegers(raiz.posAtual(), item);
            if (lista.isPresent()) return lista;
        }
        return Optional.empty();
    }

    // TODO Arrumar um nome decente para essa função
    private Optional<ArrayList<Integer>> getIntegers(final int raiz,
                                                     final int posicao) throws IOException, InterruptedException {
        if (!visitados.add(posicao)) {
            return Optional.empty();
        }

        final var node = ChamoAPI.proxMovimento(posicao);

        if (node.fim()) return finalDoCaminho(node, raiz);

        final var caminho = dfs(node);
        caminho.map((caminho1) -> caminho1.add(raiz));

        if (caminho.isEmpty()) {
            //noinspection ResultOfMethodCallIgnored
            ChamoAPI.proxMovimento(raiz);
        }

        return caminho;
    }

    @Contract(pure = true)
    private @NotNull Function<String, Node> node() {
        return (json) -> gson.fromJson(json, Node.class);
    }
}

