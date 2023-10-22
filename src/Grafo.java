import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Grafo {
    // O HashMap é um par de chaves(K) e valores(V).
    // K representa um vértice do grafo e V representa seus vizinhos.
    private final @NotNull HashMap<@NotNull Integer, @NotNull ArrayList<Integer>> vizinhos;

    public Grafo(@NotNull final Node No) {
        this.vizinhos = new HashMap<>();
        this.vizinhos.put(No.posAtual(), No.vizinhos());
    }

    public final void insereGrafo(@NotNull final Node No) {
        this.vizinhos.putIfAbsent(No.posAtual(), No.vizinhos());
    }

    @Contract(pure = true)
    public final Optional<ArrayList<Integer>> getVizinhosNo(final int no) {
        return Optional.ofNullable(this.vizinhos.get(no));
    }
}
