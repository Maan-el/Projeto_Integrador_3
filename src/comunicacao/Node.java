package comunicacao;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record Node(@SerializedName("pos_atual") @NotNull Integer posAtual,
                   @SerializedName("Comunicação.Inicio") boolean inicio,
                   @SerializedName("final") boolean fim,
                   @SerializedName("movimentos") @NotNull ArrayList<Integer> vizinhos) {
}
