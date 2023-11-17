import comunicacao.Node;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public record Grafo(@NotNull Integer inicio,
                    @NotNull HashSet<Integer> saidas,
                    @NotNull HashMap<@NotNull Integer, @NotNull ArrayList<@NotNull Integer>> listaAdjacencia) {
    public Optional<ArrayList<Integer>> get(@NotNull final Integer posicao) {
        return Optional.ofNullable(listaAdjacencia.get(posicao));
    }

    public void add(@NotNull final Node node) {
        listaAdjacencia.putIfAbsent(node.posAtual(), node.vizinhos());

        if (node.fim()) saidas.add(node.posAtual());
    }
}
