import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public final class Node {
    @SerializedName("pos_atual")
    private int posAtual;
    @SerializedName("inicio")
    private boolean inicio;
    @SerializedName("final")
    private boolean fim;
    @SerializedName("movimentos")
    private Vector<Integer> vizinhos;

    public boolean isFim() {
        return fim;
    }

    public @NotNull Vector<Integer> getVizinhos() {
        return vizinhos;
    }

    public int getPosAtual() {
        return posAtual;
    }

    public boolean isInicio() {
        return inicio;
    }
}
