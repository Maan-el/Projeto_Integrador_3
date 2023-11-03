import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public record Movimento(@SerializedName("id") @NotNull String nome,
                        @SerializedName("labirinto") @NotNull String labirinto,
                        @SerializedName("nova_posicao") @NotNull Integer posicao) {
}
