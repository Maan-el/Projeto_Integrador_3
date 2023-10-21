import com.google.gson.annotations.SerializedName;

public record CaminhoValido(@SerializedName("caminho_valido") boolean caminho_valido,
                            @SerializedName("quantidade_movimentos") int quantidade_movimentos) {
}
