import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashSet;

public class BFS {
    private final @NotNull Grafo grafo;

    // Essa fila irá conter os próximos valores a serem visitados no grafo.
    private final @NotNull ArrayDeque<Integer> fila;

    // Um set é uma estrutura de dados que mantem uma cópia de todos os valores inseridos nele.
    // Exemplo, digamos que eu insiro os numéro 3, 7, 6, caso tente inserir qualquer um desses
    // números novamente a estrutura verá que esse valor já existe e não irá inserí-lo,
    // retornando um erro (false).
    private final @NotNull HashSet<Integer> visitados;

    public BFS(@NotNull final Grafo grafo) {
        this.grafo = grafo;
        this.fila = new ArrayDeque<>();
        this.visitados = new HashSet<>();
    }

    private void addList(final int no) {
        grafo.getVizinhosNo(no).ifPresent((xs) -> {
            xs.stream()
                    .filter(visitados::add)
                    .forEach(fila::add);
        });
    }
}
