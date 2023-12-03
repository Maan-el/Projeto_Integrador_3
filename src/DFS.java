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
    private final API api;
    @NotNull
    private final HashSet<Integer> visitados;

    public DFS(@NotNull final API api) {
        this.api = api;
        this.visitados = new HashSet<>();
    }

    final public @NotNull ArrayList<Integer> inicio() throws IOException, InterruptedException {
        final Node node = api.inicio();

        visitados.add(node.posAtual());

        // O optional existe para uso interno do DFS,
        // ápara representar uma posição onde não existem movimentos vãlidos
        //noinspection OptionalGetWithoutIsPresent
        return dfs(node)
                .map(fixCaminho())
                .get();
    }

    @Contract(pure = true)
    private @NotNull Function<List<Integer>, ArrayList<Integer>> fixCaminho() {
        return (caminho) -> new ArrayList<>(caminho.reversed());
    }

    @Contract(pure = true)
    private Optional<ArrayList<Integer>> dfs(@NotNull final Node raiz) throws IOException, InterruptedException {
        for (var posicao : raiz.vizinhos()) {
            final var lista = verificaValores(raiz.posAtual(), posicao);
            if (lista.isPresent()) return lista;
        }
        return Optional.empty();
    }

    @Contract(pure = true)
    private @NotNull Optional<ArrayList<Integer>> verificaValores(final int raiz,
                                                                  final int posicao) throws IOException, InterruptedException {
        if (!visitados.add(posicao)) {
            return Optional.empty();
        }

        final var node = api.proxMovimento(posicao);

        if (node.fim()) return finalDoCaminho(node, raiz);

        final var maybeCaminho = dfs(node)
                .map((caminho) -> {
                    caminho.add(raiz);
                    return caminho;
                });

        if (maybeCaminho.isEmpty()) {
            //noinspection ResultOfMethodCallIgnored
            api.proxMovimento(raiz);
        }

        return maybeCaminho;
    }

    @Contract("_, _ -> new")
    @NotNull
    private Optional<ArrayList<Integer>> finalDoCaminho(@NotNull final Node node, final int raiz) {
        return Optional.of(new ArrayList<>(List.of(node.posAtual(), raiz)));
    }
}

