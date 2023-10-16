import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.Vector;

public class Grafo {

    private final @NotNull Hashtable<Integer, Vector<Integer>> vizinhos;

    public Grafo(final int key,
                 @NotNull final Vector<Integer> viz) {
        this.vizinhos = new Hashtable<>(800);
        this.vizinhos.put(key, viz);
    }

    @Contract(pure = true)
    public Grafo() {
        this.vizinhos = new Hashtable<>(800);
    }

    public final void insereGrafo(int key,
                                  @NotNull Vector<Integer> viz) {
        this.vizinhos.putIfAbsent(key, viz);
    }

    @Contract(pure = true)
    public final @NotNull Hashtable<Integer, Vector<Integer>> getVizinhos() {
        return vizinhos;
    }

    @Contract(pure = true)
    public final Vector<Integer> getVizinhosNo(int No) {
        return this.vizinhos.get(No);
    }
}
