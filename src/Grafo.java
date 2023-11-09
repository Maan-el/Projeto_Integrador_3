public class Grafo {
import comunicacao.Node;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public record Grafo(@NotNull Integer inicio,
                    @NotNull HashSet<Integer> saidas,
                    @NotNull HashMap<@NotNull Integer, @NotNull ArrayList<@NotNull Integer>> listaAdjacencia) {
    public void add(@NotNull final Node node) {
}
