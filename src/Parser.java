import com.google.gson.Gson;
import comunicacao.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Parser {
    private final Gson gson;

    public Parser() {
        this.gson = new Gson();
    }

    @Contract(pure = true)
    public final @NotNull Function<String, Node> toNode() {
        return (json) -> gson.fromJson(json, Node.class);
    }

    @Contract(pure = true)
    public final @NotNull Function<String, Inicio> toInicio() {
        return (json) -> gson.fromJson(json, Inicio.class);
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
}
