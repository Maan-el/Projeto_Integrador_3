import com.google.gson.Gson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ChamoAPI {
    private final static HttpClient client = HttpClient.newHttpClient();

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
        return "sample_maze";
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

    @Contract(pure = true)
    final public String getNomes() throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create(getUrlNomesLab()))
                .build();

        final HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @Contract(pure = true)
    final public String inicio() throws IOException, InterruptedException {
        final String json = "{\n" +
                "\t\"id\": \"" + getID() + "\",\n" +
                "\t\"labirinto\": \"" + getNomeLabirinto() + "\"\n" +
                "}";

        return sendRequest(URI.create(getUrlInicio()), json);
    }

    @Contract(pure = true)
    final public @NotNull String proxMovimento(final int No) throws IOException, InterruptedException {
        final String json = "{\n" +
                "\t\"id\" :" + "\"" + getID() + "\",\n" +
                "\t\"labirinto\": " + "\"" + getNomeLabirinto() + "\",\n" +
                "\t\"nova_posicao\": " + No + "\n" +
                "}";

        return sendRequest(URI.create(getUrlMovimento()), json);
    }

    // Not tested
    @Contract(pure = true)
    final public @NotNull caminhoValido fim(final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        final Gson gson = new Gson();
        final String json = "{\n" +
                "\t\"id\" :" + "\"" + getID() + "\"" + "\n" +
                "\t\"labirinto\" :" + "\"" + getNomeLabirinto() + "\"" + "\n" +
                "\t\" \"todos_movimentos: " + gson.toJson(caminho) + "\n" +
                "}";


        final String retorno = sendRequest(URI.create(getUrlValidador()), json);

        return gson.fromJson(retorno, caminhoValido.class);
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
