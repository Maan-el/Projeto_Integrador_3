import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record validaCaminho (@NotNull String id, @NotNull String nome, @NotNull ArrayList<Integer> caminho) {

}
