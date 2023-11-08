import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;

public class BFS {
    private final @NotNull Grafo grafo;

    // Essa fila irá conter os próximos valores a serem visitados no grafo.
    private final @NotNull ArrayDeque<Integer> fila;

    //    Private final @NotNull HashSet<Integer> saidas;
    // Um set é uma estrutura de dados que mantem uma cópia de todos os valores inseridos nele.
    // Exemplo, digamos que eu insiro os numéro 3, 7, 6, caso tente inserir qualquer um desses
    // números novamente a estrutura verá que esse valor já existe e não irá inserí-lo,
    // retornando um erro (false).
    private final @NotNull HashSet<Integer> visitados;

    public BFS(@NotNull final Grafo grafo /*, @NotNull final HashSet<Integer> saidas*/) {
//        this.saidas = saidas;
        this.grafo = grafo;
        this.fila = new ArrayDeque<>();
        this.visitados = new HashSet<>();
    }

    public void addList(final @NotNull Integer no) {
        grafo.getVizinhosNo(no)
                .ifPresent((xs) -> {
                    xs.stream() // Stream é uma função que recebe uma estrutura de dados e retorna um iterador sobre essa função
                            .filter(visitados::add) // filter() é uma função que recebe um iterador e uma função que retorna um booleano.
                            // Ela passa o valor do iterador para a função e,
                            // caso a função retorne true, o filter retorna o iterador.
                            // Caso seja falso, ele não passa o valor a frente.
                            .forEach(fila::add); // forEach() pega o iterador retornado pela chamada anterior e o aplica em uma função passada para ele.
                });
    }

    final public Optional<Integer> proxNo() {
        if (fila.isEmpty()) return Optional.empty();

        return Optional.of(fila.pop());
    }
}
