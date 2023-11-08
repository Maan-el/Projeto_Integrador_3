import comunicacao.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Grafo {
    private final @NotNull Set<@NotNull Integer> saidas;
    // O HashMap é um par de chaves(K) e valores(V).
    // K representa um vértice do grafo e V representa seus vizinhos.
    private final @NotNull HashMap<@NotNull Integer, @NotNull ArrayList<Integer>> vizinhos;

    {
        saidas = new HashSet<>();
        vizinhos = new HashMap<>();
    }

    /**
     * Constructor
     */
    public Grafo() {
    }

    public final void putNode(@NotNull final Node No) {
        this.vizinhos.put(No.posAtual(), No.vizinhos());

        if (No.fim()) saidas.add(No.posAtual());
    }

    @Contract(pure = true)
    public final Optional<ArrayList<Integer>> getVizinhosNo(final int no) {
        return Optional.ofNullable(this.vizinhos.get(no));
    }

    final public HashMap<Integer, ArrayList<Integer>> getGrafo() {
        return this.vizinhos;
    }

    @Contract(value = " -> new", pure = true)
    final public @NotNull Optional<Set<Integer>> getSaidas() {
        return Optional.of(saidas);
    }
}
