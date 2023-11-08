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
    private final URI movimentoUri = URI.create("https://gtm.delary.dev/movimentar");
    private final URI inicioUri = URI.create("https://gtm.delary.dev/iniciar");
    private final URI validarUri = URI.create("https://gtm.delary.dev/validar");
    private final URI nomesLabirintosUri = URI.create("https://gtm.delary.dev/labirintos");
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
        final HttpRequest requisicao = getHttpGetRequest();

        final HttpResponse<String> resposta = client.send(requisicao, HttpResponse.BodyHandlers.ofString());

        return resposta.body();
    }

    private HttpRequest getHttpGetRequest() {
        return HttpRequest.newBuilder().GET().uri(nomesLabirintosUri).build();
    }

    final public Node inicio() throws IOException, InterruptedException {
        final Inicio inicio = new Inicio(nomeGrupo, nomeLabirinto);
        final String mensagem = gson.toJson(inicio);
        final String resposta = sendRequest(inicioUri, mensagem);

        return gson.fromJson(resposta, Node.class);
    }

    /**
     * @param No Próximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public Node proxMovimento(final int No) throws IOException, InterruptedException {
        final String mensagem =
                gson.toJson(new Movimento(this.nomeGrupo, this.nomeLabirinto, No));
        final String resposta = sendRequest(this.movimentoUri, mensagem);

        return toNode(resposta);
    }

    private Node toNode(@NotNull String json) {
        return gson.fromJson(json, Node.class);
    }

    /**
     * @param caminho Lista com os passos para ir do início a saída do grafo
     * @return Classe com a resposta da API dizendo se o caminho é válido e a quantidade de movimentos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    // Not tested
    @Contract(pure = true)
    final public @NotNull CaminhoValidado fim(final ArrayList<Integer> caminho)
            throws IOException, InterruptedException {
        CaminhoParaValidar validaCaminho =
                new CaminhoParaValidar(this.nomeGrupo, this.nomeLabirinto, caminho);

        final String mensagem = gson.toJson(validaCaminho);

        final String resposta = sendRequest(this.validarUri, mensagem);

        return gson.fromJson(resposta, CaminhoValidado.class);
    }

    /**
     * @param uri  Para onde a mensagem será enviada
     * @param json Mensagem que será enviada.
     * @return Caso a conexão retorne um código de erro, todo o programa irá parar.
     * Caso o processo retorne normalmente, será retornado o json gerado pela conexão.
     * @throws IOException          Problemas com a conexão
     * @throws InterruptedException Processo foi interrompido durante a conexão
     */
    @Contract(pure = true)
    private String sendRequest(final @NotNull URI uri, final @NotNull String json)
            throws IOException, InterruptedException {

        final HttpRequest mensagem = getHttpPostRequest(uri, json);
        final HttpResponse<String> resposta =
                client.send(mensagem, HttpResponse.BodyHandlers.ofString());

        validaRetorno(resposta);

        return resposta.body();
    }

    private HttpRequest getHttpPostRequest(@NotNull URI uri, @NotNull String json) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    /**
     * A API tem apenas dois valores, sucesso e erro.
     * Caso encontremos o estado de erro, crashar o programa
     *
     * @param resposta Comunicação com a API que precisa ser validada
     */
    private <T> void validaRetorno(final @NotNull HttpResponse<T> resposta) {
        /**
         * A API, no momento, possui apenas dois códigos de resposta, 200 e 422.
         * O erro é gerado caso haja um erro de formatação no json _ou_ um movimento inválido ocorre.
         * Para facilitar o processo de debug, caso ocorra um erro, o programa irá crashar instantaneamente.
         */
        final int ERROR_CODE = 422;
        if (resposta.statusCode() == ERROR_CODE) {
            System.err.println("Movimento inválido realizado");
            System.err.println("Parando execução");
            System.exit(-1);
        }
    }
}
