import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Grafo {
    private final @NotNull HashMap<@NotNull Integer, @NotNull ArrayList<Integer>> vizinhos;

    public Grafo(@NotNull final Node No) {
        this.vizinhos = new HashMap<>();
        this.vizinhos.put(No.posAtual(), No.vizinhos());
    }

    public final void insereGrafo(@NotNull final Node No) {
        this.vizinhos.putIfAbsent(No.posAtual(), No.vizinhos());
    }

    @Contract(pure = true)
    public final Optional<ArrayList<Integer>> getVizinhosNo(int No) {
        return Optional.ofNullable(this.vizinhos.get(No));
    }

    public final @NotNull HashMap<@NotNull Integer, @NotNull ArrayList<Integer>> getMap() {
        return Objects.requireNonNull(vizinhos);
    }
}
