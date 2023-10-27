import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DFS {
    //    Stack<Integer> stack;
    Grafo grafo;
    Gson gson;
    ChamoAPI API;
    HashSet<Integer> visitados;

    public DFS() {
//        stack = new Stack<>();
        visitados = new HashSet<>();
        gson = new Gson();
        API = new ChamoAPI();
    }

    final public Grafo inicio() throws IOException, InterruptedException {

        String json = API.inicio().orElse("");
        Node node = gson.fromJson(json, Node.class);
        grafo = new Grafo(node);

        dfs(node.posAtual(), node.vizinhos());

        return this.grafo;
    }

    // TODO: Existe um corner case em que, caso um nó não
    //  tenha vizinhos restantes ele não entrará no loop,
    //  apenas retornará, sem mover de volta a posição
    //  original, causando um movimento inválido.
    private void dfs(final int raiz, final ArrayList<Integer> vizinhos) throws IOException, InterruptedException {
        visitados.add(raiz);
        if (vizinhos == null) {
            return;
        }

        for (int item : vizinhos) {
            if (visitados.add(item)) {
                String json = API.proxMovimento(item);
                Node node = gson.fromJson(json, Node.class);

                grafo.putGrafo(node);

                dfs(node.posAtual(), node.vizinhos());
                API.proxMovimento(raiz);
            }
        }
        System.out.println(grafo.getGrafo().size());
    }
}

