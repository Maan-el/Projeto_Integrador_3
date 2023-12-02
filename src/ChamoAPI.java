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
import java.util.function.Function;

public class ChamoAPI {
    private final Gson gson;
    private final HttpClient client;
    private final String nomeLabirinto;
    private final String id;
    private final URI nome;
    private final URI inicio;
    private final URI movimento;
    private final URI validacao;

    @Contract(pure = true)
    public ChamoAPI() {
        id = "Um-Grupo";
        nomeLabirinto = "large-maze";
        gson = new Gson();
        nome = URI.create("https://gtm.delary.dev/" + "labirintos");
        inicio = URI.create("https://gtm.delary.dev/" + "iniciar");
        movimento = URI.create("https://gtm.delary.dev/" + "movimentar");
        validacao = URI.create("https://gtm.delary.dev/" + "validar_caminho");
        client = HttpClient.newHttpClient();
    }

    @NotNull
    private HttpResponse.BodyHandler<String> getOfString() {
        return HttpResponse.BodyHandlers.ofString();
    }

    /**
     * @return Retorna os nomes dos labirintos válidos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public String getNomes() throws IOException, InterruptedException {
        final var request = HttpRequest.newBuilder().GET().uri(this.nome).build();

        final var response = client.send(request, getOfString());

        return response.body();
    }

    @Contract(pure = true)
    final public @NotNull String getLabirinto() {
        return this.nomeLabirinto;
    }

    final public String inicio() throws IOException, InterruptedException {

        final var inicio = new Inicio(this.id, this.nomeLabirinto);
        final String json = gson.toJson(inicio);

        return sendRequest(this.inicio, json);
    }

    /**
     * @param no Proximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public @NotNull String proxMovimento(final int no) throws IOException, InterruptedException {
        final var movimento = new Movimento(this.id, this.nomeLabirinto, no);
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
    final public @NotNull CaminhoValidado validaCaminho(final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        final var validaCaminho = new CaminhoParaValidar(this.id, this.nomeLabirinto, caminho);

        final String json = gson.toJson(validaCaminho);

        final var caminhoValidado = sendRequest(this.validacao, json).transform(validado());

//        if (caminhoValidado.caminho_valido()) {
//            caminho.removeFirst();
//            gotoFim(caminho);
//        }

        return caminhoValidado;
    }

    @NotNull
    private Function<String, CaminhoValidado> validado() {
        return (retorno) -> gson.fromJson(retorno, CaminhoValidado.class);
    }

    @Contract(pure = true)
    private void gotoFim(@NotNull final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        for (int posicao : caminho) proxMovimento(posicao);
    }

    private void gotoFimUnsafe(@NotNull final ArrayList<Integer> caminho) {
        for (int posicao : caminho) proxMovimentoUnsafe(posicao);
    }

    private void proxMovimentoUnsafe(final int posicao) {
        Movimento movimento = new Movimento(this.id, this.nomeLabirinto, posicao);
        final String json = gson.toJson(movimento);

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(this.movimento)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.sendAsync(request, getOfString());
    }

    @Contract(pure = true)
    private String sendRequest(final URI uri, final String json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(json))
                .build();

        return client.send(request, getOfString()).body();
    }
}
