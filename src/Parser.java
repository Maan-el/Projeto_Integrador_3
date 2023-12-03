import com.google.gson.Gson;
import comunicacao.Inicio;
import comunicacao.Movimento;
import comunicacao.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Parser {
    private final Gson gson;

    public Parser() {
        this.gson = new Gson();
    }
}
