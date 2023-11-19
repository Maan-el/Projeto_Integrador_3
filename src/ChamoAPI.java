import com.google.gson.Gson;
import comunicacao.CaminhoParaValidar;
import comunicacao.CaminhoValidado;
import comunicacao.Inicio;
import comunicacao.Movimento;
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
    private final Gson gson;
    private final HttpClient client = HttpClient.newHttpClient();

    @Contract(pure = true)
    public ChamoAPI() {
        gson = new Gson();
    }

    @NotNull
    @Contract(" -> new")
    private URI getUriNome() {
        return URI.create("https://gtm.delary.dev/" + "labirintos");
    }

    @NotNull
    @Contract(" -> new")
    private URI getUriInicio() {
        return URI.create("https://gtm.delary.dev/" + "iniciar");
    }

    @NotNull
    @Contract(" -> new")
    private URI getUriMovimento() {
        return URI.create("https://gtm.delary.dev/" + "movimentar");
    }

    @Contract(" -> new")
    private @NotNull URI getUriValidacao() {
        return URI.create("https://gtm.delary.dev/" + "validar_caminho");
    }

    @NotNull
    private HttpResponse.BodyHandler<String> getOfString() {
        return HttpResponse.BodyHandlers.ofString();
    }

    @NotNull
    @Contract(pure = true)
    private String getID() {
        return "Um_Grupo";
    }

    @NotNull
    @Contract(pure = true)
    private String getNomeLabirinto() {
        return "large-maze";
    }

    /**
     * @return Retorna os nomes dos labirintos válidos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public String getNomes() throws IOException, InterruptedException {
        final var request = HttpRequest.newBuilder().GET().uri(getUriNome()).build();

        final var response = client.send(request, getOfString());

        return response.body();
    }

    @Contract(pure = true)
    final public @NotNull String getLabirinto() {
        return getNomeLabirinto();
    }

    final public Optional<String> inicio() {

        String resposta;

        final Inicio inicio = new Inicio(getID(), getNomeLabirinto());
        final String json = gson.toJson(inicio);

        try {
            resposta = sendRequest(getUriInicio(), json);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(resposta);
    }

    /**
     * @param No Proximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public @NotNull String proxMovimento(final int No) throws IOException, InterruptedException {
        Movimento movimento = new Movimento(getID(), getNomeLabirinto(), No);
        final String json = gson.toJson(movimento);

        return sendRequest(getUriMovimento(), json);
    }

    /**
     * @param caminho Lista com os passos para ir do início a saída do grafo
     * @return Classe com a resposta da API dizendo se o caminho é válido e a quantidade de movimentos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    // Not tested
    @Contract(pure = true)
    final public @NotNull CaminhoValidado validaCaminho(final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        CaminhoParaValidar validaCaminho = new CaminhoParaValidar(getID(), getNomeLabirinto(), caminho);

        final String json = gson.toJson(validaCaminho);

        final String retorno = sendRequest(getUriValidacao(), json);

        CaminhoValidado caminhoValidado = gson.fromJson(retorno, CaminhoValidado.class);
        if (caminhoValidado.caminho_valido()) {
            caminho.removeFirst();
            gotoFim(caminho);
        }

        return caminhoValidado;
    }

    @Contract(pure = true)
    private void gotoFim(@NotNull final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        for (int posicao : caminho) proxMovimento(posicao);
    }

    private void gotoFimUnsafe(@NotNull final ArrayList<Integer> caminho) {
        for (int posicao : caminho) proxMovimentoUnsafe(posicao);
    }

    private void proxMovimentoUnsafe(final int posicao) {
        Movimento movimento = new Movimento(getID(), getNomeLabirinto(), posicao);
        final String json = gson.toJson(movimento);

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(getUriMovimento())
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.sendAsync(request, getOfString());
    }

    @Contract(pure = true)
    private String sendRequest(final URI uri, final String json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).headers("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

        return client.send(request, getOfString()).body();
    }
}
