package comunicacao;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Function;

public record CaminhoParaValidar(@NotNull @SerializedName("id") String nomeGrupo,
                                 @NotNull @SerializedName("labirinto") String nomeLabirinto,
                                 @NotNull @SerializedName("todos_movimentos") ArrayList<Integer> caminho) {
    public <R> @NotNull R transform(@NotNull Function<CaminhoParaValidar, R> f) {
        return f.apply(this);
    }

}
