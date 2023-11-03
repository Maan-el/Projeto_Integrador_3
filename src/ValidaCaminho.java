import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record ValidaCaminho(@NotNull String id, @NotNull String nome, @NotNull ArrayList<Integer> caminho) {

}
