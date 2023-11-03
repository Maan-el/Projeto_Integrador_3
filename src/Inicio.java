import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public record Inicio(@SerializedName("id") @NotNull String nome,
                     @SerializedName("labirinto") @NotNull String labirinto) {
}
