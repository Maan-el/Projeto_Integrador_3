import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public record Node(@SerializedName("pos_atual") int posAtual,
                   @SerializedName("inicio") boolean inicio,
                   @SerializedName("final") boolean fim,
                   @SerializedName("movimentos") ArrayList<Integer> vizinhos) {
}
