import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class Visitados {
    private final @NotNull HashSet<@NotNull Integer> v;

    public Visitados() {
        this.v = new HashSet<>();
    }

    public boolean visitado(final int no) {
        return !v.add(no);
    }
}
