package comunicacao;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record ValidaCaminho(@NotNull @SerializedName("id") String nomeGrupo,
                            @NotNull @SerializedName("nome") String nomeLabirinto,
                            @NotNull @SerializedName("caminho") ArrayList<Integer> caminho) {

}
