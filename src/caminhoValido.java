import com.google.gson.annotations.SerializedName;

public class caminhoValido {
    @SerializedName("caminho_valido")
    private boolean caminho_valido;
    @SerializedName("quantidade_movimentos")
    private int quantidade_movimentos;

    public final int getQuantidade_movimentos() {
        return quantidade_movimentos;
    }

    public final boolean isCaminho_valido() {
        return caminho_valido;
    }
}
