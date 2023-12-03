package comunicacao;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public record Inicio(@SerializedName("id") @NotNull String nome,
                     @SerializedName("labirinto") @NotNull String labirinto) {
    public <R> @NotNull R transform(@NotNull Function<Inicio, R> f) {
        return f.apply(this);
    }
}
