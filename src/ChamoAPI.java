import comunicacao.*;
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
    private final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();

    private final URI movimento = URI.create("https://gtm.delary.dev/movimentar");
    private final URI inicio = URI.create("https://gtm.delary.dev/iniciar");
    private final URI validar = URI.create("https://gtm.delary.dev/validar");
    private final URI nomeLabirintos = URI.create("https://gtm.delary.dev/labirintos");
    private final @NotNull String nomeGrupo = "Um_Grupo";

    private final @NotNull String nomeLabirinto;

    /**
     * Constructor
     */
    @Contract(pure = true)
    public ChamoAPI() {
        this.nomeLabirinto = "maze-sample";
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
                .uri(nomeLabirintos)
                .build();

        final var response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    final public Node toNode(@NotNull final String json) {
        return gson.fromJson(json, Node.class);
    }

    final public String inicio() throws IOException, InterruptedException {
        Inicio inicio = new Inicio(nomeGrupo, nomeLabirinto);
        final String json = gson.toJson(inicio);

        return sendRequest(this.inicio, json);
    }

    /**
     * @param No Proximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public String proxMovimento(final int No) throws IOException, InterruptedException {
        Movimento movimento = new Movimento(this.nomeGrupo, this.nomeLabirinto, No);
        final String json = gson.toJson(movimento);

        return sendRequest(this.movimento, json);
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
        ValidaCaminho validaCaminho = new ValidaCaminho(this.nomeGrupo, this.nomeLabirinto, caminho);

        final String json = gson.toJson(validaCaminho);

        final String retorno = sendRequest(this.validar, json);

        return gson.fromJson(retorno, CaminhoValido.class);
    }

    /**
     * @param uri
     * @param json
     * @return Caso a conexão retorne um código de erro, todo o programa irá parar.
     * Caso o processo retorne normalmente, será retornado o json gerado pela conexão.
     * @throws IOException
     * @throws InterruptedException
     */
    @Contract(pure = true)
    private String sendRequest(final URI uri, final String json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        validaRetorno(request);

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }


    /**
     * @param request
     * @throws IOException
     * @throws InterruptedException Verifica que a conexão retorna algo válido, senão, para de executar o programa.
     */
    private void validaRetorno(HttpRequest request) throws IOException, InterruptedException {
        if (client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 422) {
            System.err.println("Comunicação.Movimento inválido");
            System.err.println("Parando execução");
            System.exit(-1);
        }
    }
}
