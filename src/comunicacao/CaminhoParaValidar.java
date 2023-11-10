package comunicacao;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record CaminhoParaValidar(@NotNull @SerializedName("id") String nomeGrupo,
                                 @NotNull @SerializedName("labirinto") String nomeLabirinto,
                                 @NotNull @SerializedName("todos_movimentos") ArrayList<Integer> caminho) {

}
