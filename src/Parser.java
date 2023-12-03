import com.google.gson.Gson;
import comunicacao.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Function;

public class Parser {
    private final Gson gson;
    private final String nomeGrupo;
    private final String nomeLabirinto;

    public Parser() {
        this.gson = new Gson();
        this.nomeGrupo = "Um-Grupo";
        this.nomeLabirinto = "large-maze";
    }

    @Contract(" -> new")
    public final @NotNull Inicio newInicio() {
        return new Inicio(this.nomeGrupo, this.nomeLabirinto);
    }

    @Contract("_ -> new")
    public final @NotNull Movimento newMovimento(final int posicao) {
        return new Movimento(this.nomeGrupo, this.nomeLabirinto, posicao);
    }

    @Contract("_ -> new")
    public final @NotNull CaminhoParaValidar newCaminhoParaValidar(@NotNull final ArrayList<Integer> caminho) {
        return new CaminhoParaValidar(this.nomeGrupo, this.nomeLabirinto, caminho);
    }

    @Contract(pure = true)
    public final @NotNull Function<String, Inicio> toInicio() {
        return (json) -> gson.fromJson(json, Inicio.class);
    }

    @Contract(pure = true)
    public final @NotNull Function<String, Node> toNode() {
        return (json) -> gson.fromJson(json, Node.class);
    }

    @Contract(pure = true)
    public final @NotNull Function<String, Movimento> toMovimento() {
        return (json) -> gson.fromJson(json, Movimento.class);
    }

    @Contract(pure = true)
    public final @NotNull Function<String, CaminhoParaValidar> toCaminhoParaValidar() {
        return (json) -> gson.fromJson(json, CaminhoParaValidar.class);
    }

    @Contract(pure = true)
    public final @NotNull Function<String, CaminhoValidado> toCaminhoValidado() {
        return (json) -> gson.fromJson(json, CaminhoValidado.class);
    }

    @Contract(pure = true)
    public final @NotNull Function<Inicio, String> fromInicio() {
        return gson::toJson;
    }

    @Contract(pure = true)
    public final @NotNull Function<Node, String> fromNode() {
        return gson::toJson;
    }

    @Contract(pure = true)
    public final @NotNull Function<Movimento, String> fromMovimento() {
        return gson::toJson;
    }

    @Contract(pure = true)
    public final @NotNull Function<CaminhoParaValidar, String> fromCaminhoParaValidar() {
        return gson::toJson;
    }

    @Contract(pure = true)
    public final @NotNull Function<CaminhoValidado, String> fromCaminhoValidado() {
        return gson::toJson;
    }
}