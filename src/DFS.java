import comunicacao.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

public class DFS {
    @NotNull
    final ChamoAPI API;
    @NotNull
    final HashSet<Integer> visitados;

    public DFS() {
        visitados = new HashSet<>();
        API = new ChamoAPI();
    }

    @Nullable
    final public ArrayList<Integer> inicio() throws IOException, InterruptedException {
        Node node = API.inicio();

        visitados.add(node.posAtual());
        return dfs(node.posAtual(), node.vizinhos()).orElse(null);
    }

    private Optional<ArrayList<Integer>> dfs(@NotNull final Integer raiz,
                                             @NotNull final ArrayList<Integer> vizinhos) throws IOException, InterruptedException {
        Optional<ArrayList<Integer>> retorno = Optional.empty();

        for (var item : vizinhos) {
            if (visitados.add(item)) {
                Node node = API.proxMovimento(item);

                if (node.fim()) return Optional.of(new ArrayList<>(node.posAtual()));

                retorno = dfs(node.posAtual(), node.vizinhos());

                if (retorno.isPresent()) break;
                //noinspection ResultOfMethodCallIgnored
                API.proxMovimento(raiz);
            }
        }
        retorno.ifPresent((xs) -> xs.add(raiz));
        return retorno;
    }

}

