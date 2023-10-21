import com.google.gson.Gson;

import java.io.IOException;

// Todas as classes que estão sendo usadas somente para pegar valores de uma API foram substituidas por Records,
// pois elas possuiam apenas getters e todos os seus valores eram constantes,
// por isso Record declara intenção melhor que Class.

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ChamoAPI response = new ChamoAPI();
        String resposta = response.inicio();

        Gson gson = new Gson();
        Node node = gson.fromJson(resposta, Node.class);

        Grafo grafo = new Grafo(node);

        String next = response.proxMovimento(4);
        node = gson.fromJson(next, Node.class);

        grafo.insereGrafo(node);
        grafo.getVizinhosNo(node.posAtual())
                .ifPresent(System.out::println);
    }
}