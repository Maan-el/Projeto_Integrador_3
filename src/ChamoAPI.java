import com.google.gson.Gson;
import comunicacao.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;

public class ChamoAPI {
    @NotNull
    private final Gson gson = new Gson();
    @NotNull
    private final HttpClient client = HttpClient.newHttpClient();
    @NotNull
    private final String nomeGrupo = "Um_Grupo";
    @NotNull
    private final String nomeLabirinto = "very-large-maze";

    /**
     * Constructor
     */
    @Contract(pure = true)
    public ChamoAPI() {
    }

    final public Node inicio() throws IOException, InterruptedException {
        final Inicio inicio = new Inicio(nomeGrupo, nomeLabirinto);
        final String mensagem = gson.toJson(inicio);
        final String resposta = sendRequest(URI.create("https://gtm.delary.dev/iniciar"), mensagem);

        return toNode(resposta);
    }

    /**
     * @return Retorna os nomes dos labirintos válidos
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public String getNomes() throws IOException, InterruptedException {
        final HttpRequest requisicao = geraRequisicao();

        final HttpResponse<String> resposta = client.send(requisicao, getJson());

        return resposta.body();
    }

    /**
     * @param posicao Próximo nó a ser enviado para a API
     * @return String com o json bruto retornado pela API
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    @Contract(pure = true)
    final public Node movePara(@NotNull final Integer posicao) throws IOException, InterruptedException {
        final String mensagem = criaMensagemJson(geraMovimento(posicao));
        final String resposta = sendRequest(URI.create("https://gtm.delary.dev/movimentar"), mensagem);

        return toNode(resposta);
    }

    /**
     * @param caminho Lista com os passos para ir do início a saída do grafo
     * @throws IOException          Erro de conexão
     * @throws InterruptedException ^C (Processo cancelado)
     */
    // Not tested
    @Contract(pure = true)
    final public void fim(@NotNull final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        if (getCaminhoValidado(caminho).caminho_valido()) {
            caminhaParaSaida(caminho);
        } else {
            caminhaParaSaida(caminho);
        }
    }

    private Node toNode(@NotNull String json) {
        return gson.fromJson(json, Node.class);
    }

    private String criaMensagemJson(final @NotNull Movimento movimento) {
        return gson.toJson(movimento);
    }

    private CaminhoValidado getCaminhoValidado(@NotNull final String resposta) {
        return gson.fromJson(resposta, CaminhoValidado.class);
    }

    private String criaMensagem(CaminhoParaValidar validaCaminho) {
        return gson.toJson(validaCaminho);
    }

    @NotNull
    @Contract("_ -> new")
    private Movimento geraMovimento(final int posicao) {
        return new Movimento(this.nomeGrupo, this.nomeLabirinto, posicao);
    }

    @NotNull
    private CaminhoParaValidar getValidaCaminho(@NotNull ArrayList<Integer> caminho) {
        return new CaminhoParaValidar(this.nomeGrupo, this.nomeLabirinto, caminho);
    }

    @NotNull
    private BodyHandler<String> getJson() {
        return HttpResponse.BodyHandlers.ofString();
    }

    @NotNull
    private BodyPublisher postJson(@NotNull final String json) {
        return HttpRequest
                .BodyPublishers
                .ofString(json);
    }

    @NotNull
    private HttpRequest getHttpPostRequest(@NotNull final URI uri, @NotNull final String json) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .POST(postJson(json))
                .build();
    }

    private HttpRequest geraRequisicao() {
        return HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create("https://gtm.delary.dev/labirintos"))
                .build();
    }

    /**
     * Essa função move o rato pelo labirinto da API.
     * Não é feita nenhuma validação no valor passado para a API, caso ocorra um erro de movimento, não haverá detecção
     *
     * @param posicao Para onde devo ir no grafo
     */
    private void moveParaUnsafe(@NotNull final Integer posicao) {
        final String mensagem = criaMensagemJson(geraMovimento(posicao));

        sendRequestUnsafe(URI.create("https://gtm.delary.dev/movimentar"), mensagem);
    }

    @NotNull
    private CaminhoValidado getCaminhoValidado(@NotNull ArrayList<Integer> caminho) throws IOException, InterruptedException {
        CaminhoParaValidar validaCaminho = getValidaCaminho(caminho);

        final String mensagem = criaMensagem(validaCaminho);

        final String resposta = sendRequest(URI.create("https://gtm.delary.dev/validar_caminho"), mensagem);

        return getCaminhoValidado(resposta);
    }

    private void caminhaParaSaida(@NotNull final ArrayList<Integer> caminho) throws IOException, InterruptedException {
        inicio();

        caminho.remove(0);

        for (Integer movimento : caminho) {
            moveParaUnsafe(movimento);
        }
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
    private String sendRequest(@NotNull final URI uri, @NotNull final String json) throws IOException, InterruptedException {
        final HttpRequest mensagem = getHttpPostRequest(uri, json);
        final HttpResponse<String> resposta = client.send(mensagem, getJson());

        validaRetorno(resposta);

        return resposta.body();
    }

    /**
     * Envia uma mensagem para a API, ignorando qualquer retorno da API
     * Criada pois o processo de enviar informação para a API é o maior gargalo do programa
     *
     * @param uri  Uri da conexão
     * @param json Mensagem a ser enviada
     */
    private void sendRequestUnsafe(@NotNull final URI uri, @NotNull final String json) {
        final HttpRequest mensagem = getHttpPostRequest(uri, json);
        client.sendAsync(mensagem, getJson());
    }


    /**
     * A API tem apenas dois valores, sucesso e erro.
     * Caso encontremos o estado de erro, crashar o programa
     *
     * @param resposta Comunicação com a API que precisa ser validada
     */
    private void validaRetorno(@NotNull final HttpResponse<String> resposta) {
        /*
         * A API, no momento, possui apenas dois códigos de resposta, 200 e 422.
         * O erro é gerado caso haja um erro de formatação no json _ou_ um movimento inválido ocorre.
         * Para facilitar o processo de debug, caso ocorra um erro, o programa irá crashar instantaneamente.
         */
        final int SUCESSO = 200;

        if (resposta.statusCode() == SUCESSO) return;
        validaErro(resposta);
    }

    private void validaErro(@NotNull HttpResponse<String> resposta) {
        final int ERROR_CODE = 422;
        final int NOT_FOUND = 404;
        if (resposta.statusCode() == ERROR_CODE) {
            System.err.println("Movimento inválido realizado");
            System.err.println("Parando execução");
            System.exit(resposta.hashCode());
        } else if (resposta.statusCode() == NOT_FOUND) {
            System.err.println("URL não encontrada");
            System.err.println("Parando execução");
            System.exit(resposta.hashCode());
        } else {
            System.err.println("Erro desconhecido encontrado");
            System.err.println("Status: " + resposta.statusCode() + "; URL: " + resposta.uri());
            System.err.println("Parando execução");
            System.exit(resposta.hashCode());
        }
    }
}
