package comunicacao;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public record Movimento(@SerializedName("id") @NotNull String nomeGrupo,
                        @SerializedName("labirinto") @NotNull String labirinto,
                        @SerializedName("nova_posicao") int posicao) {
    public <R> @NotNull R transform(@NotNull Function<Movimento, R> f) {
        return f.apply(this);
    }
}
