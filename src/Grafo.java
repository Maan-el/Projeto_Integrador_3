//import jdk.incubator.vector.IntVector;
//import jdk.incubator.vector.VectorSpecies;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.Optional;
import java.util.Vector;

public class Grafo {
    private final @NotNull Hashtable<Integer,Vector<Integer>> vizinhos;

    public Grafo(@NotNull final Node No) {
        this.vizinhos = new Hashtable<>(1024);
        this.vizinhos.put(No.getPosAtual(), No.getVizinhos());
    }

    @Contract(pure = true)
    public Grafo() {
        this.vizinhos = new Hashtable<>(1024);
    }

    public final void insereGrafo(@NotNull final Node No) {
        this.vizinhos.putIfAbsent(No.getPosAtual(), No.getVizinhos());
    }

    @Contract(pure = true)
    public final @NotNull Hashtable<Integer, Vector<Integer>> getVizinhos() {
        return vizinhos;
    }

    @Contract(pure = true)
    public final Optional<Vector<Integer>> getVizinhosNo(int No) {
        return Optional.ofNullable(this.vizinhos.get(No));
    }
}
