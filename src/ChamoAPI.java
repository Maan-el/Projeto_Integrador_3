import com.google.gson.Gson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

public class ChamoAPI {
    private final HttpClient client = HttpClient.newHttpClient();

    @Contract(pure = true)
    private @NotNull String getUrlAPI() {
        return "https://gtm.delary.dev/";
    }

    @Contract(pure = true)
    private @NotNull String getID() {
        return "Um_Grupo";
    }

    @Contract(pure = true)
    private @NotNull String getUrlNomesLab() {
        return getUrlAPI() + "labirintos";
    }

    @Contract(pure = true)
    private @NotNull String getNomeLabirinto() {
        return "very-large-maze";
    }

    @Contract(pure = true)
    private @NotNull String getUrlValidador() {
        return getUrlAPI() + "validar";
    }

    @Contract(pure = true)
    private @NotNull String getUrlMovimento() {
        return getUrlAPI() + "movimentar";
    }

    @Contract(pure = true)
    private @NotNull String getUrlInicio() {
        return getUrlAPI() + "iniciar";
    }

    @Contract(pure = true)
    public ChamoAPI() {
    }

    /**
     * @return Retorna os nomes dos labirintos válidos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public String getNomes() throws IOException, InterruptedException {
        final var request = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create(getUrlNomesLab()))
                .build();

        final var response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    final public Optional<String> inicio() {
        String saida;

        try {
            saida = __inicio();
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(saida);
    }

    @Contract(pure = true)
    private @NotNull String __inicio() throws IOException, InterruptedException {
        final String json = "{\n" +
                "\t\"id\": \"" + getID() + "\",\n" +
                "\t\"labirinto\": \"" + getNomeLabirinto() + "\"\n" +
                "}";

        return sendRequest(URI.create(getUrlInicio()), json);
    }

    /**
     * @param No Proximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public @NotNull String proxMovimento(final int No) throws IOException, InterruptedException {
        final String json = "{\n" +
                "\t\"id\" :" + "\"" + getID() + "\",\n" +
                "\t\"labirinto\": " + "\"" + getNomeLabirinto() + "\",\n" +
                "\t\"nova_posicao\": " + No + "\n" +
                "}";

        return sendRequest(URI.create(getUrlMovimento()), json);
    }

    /**
     * @param caminho Lista com os passos para ir do início a saída do grafo
     * @return Classe com a resposta da API dizendo se o caminho é válido e a quantidade de movimentos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    // Not tested
    @Contract(pure = true)
    final public @NotNull CaminhoValido fim(final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        final Gson gson = new Gson();
        final String json = "{\n" +
                "\t\"id\" :" + "\"" + getID() + "\"" + "\n" +
                "\t\"labirinto\" :" + "\"" + getNomeLabirinto() + "\"" + "\n" +
                "\t\" \"todos_movimentos: " + gson.toJson(caminho) + "\n" +
                "}";


        final String retorno = sendRequest(URI.create(getUrlValidador()), json);

        return gson.fromJson(retorno, CaminhoValido.class);
    }

    @Contract(pure = true)
    private String sendRequest(final URI uri, final String json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

         return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
