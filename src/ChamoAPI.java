import comunicacao.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ChamoAPI {
    private final Parser parser;
    private final HttpClient client;
    private final URI nome;
    private final URI inicio;
    private final URI movimento;
    private final URI validacao;

    @Contract(pure = true)
    public ChamoAPI() {
        this.nome = URI.create("https://gtm.delary.dev/" + "labirintos");
        this.inicio = URI.create("https://gtm.delary.dev/" + "iniciar");
        this.movimento = URI.create("https://gtm.delary.dev/" + "movimentar");
        this.validacao = URI.create("https://gtm.delary.dev/" + "validar_caminho");
        this.client = HttpClient.newHttpClient();
        this.parser = new Parser();
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
        final var request = HttpRequest
                .newBuilder()
                .GET()
                .uri(this.nome)
                .build();

        final var response = client.send(request, getOfString());

        return response.body();
    }

    final public Node inicio() throws IOException, InterruptedException {
        final String json = parser
                .newInicio()
                .transform(parser.fromInicio());

        return sendRequest(this.inicio, json)
                .transform(parser.toNode());
    }

    /**
     * @param posicao Proximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public @NotNull Node proxMovimento(final int posicao) throws IOException, InterruptedException {
        final String json = parser
                .newMovimento(posicao)
                .transform(parser.fromMovimento());

        return sendRequest(this.movimento, json)
                .transform(parser.toNode());
    }

    /**
     * @param caminho Lista com os passos para ir do início a saída do grafo
     * @return Classe com a resposta da API dizendo se o caminho é válido e a quantidade de movimentos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    // Not tested
    @Contract(pure = true)
    final public @NotNull CaminhoValidado validaCaminho(@NotNull final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        final String json = parser
                .newCaminhoParaValidar(caminho)
                .transform(parser.fromCaminhoParaValidar());

        return sendRequest(this.validacao, json)
                .transform(parser.toCaminhoValidado());
    }

    @Contract(pure = true)
    private void gotoFim(@NotNull final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        for (int posicao : caminho) proxMovimento(posicao);
    }

    private void gotoFimUnsafe(@NotNull final ArrayList<Integer> caminho) {
        for (int posicao : caminho) proxMovimentoUnsafe(posicao);
    }

    private void proxMovimentoUnsafe(final int posicao) {
        final String json = parser
                .newMovimento(posicao)
                .transform(parser.fromMovimento());

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
