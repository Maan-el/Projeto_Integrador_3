import com.google.gson.Gson;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        ChamoAPI response = new ChamoAPI();
        String resposta = response.inicio();

        System.out.println(resposta);

        Gson gson = new Gson();
        Node saida = gson.fromJson(resposta, Node.class);

        System.out.println(saida.getVizinhos());
        System.out.println(saida.getPosAtual());
        System.out.println(saida.isFim());
        System.out.println(saida.isInicio());

        Grafo grafo = new Grafo();
        grafo.insereGrafo(saida.getPosAtual(), saida.getVizinhos());

        System.out.println(grafo.getVizinhosNo(saida.getPosAtual()));

        String next = response.proxMovimento(4);
        System.out.println(next);
        saida = gson.fromJson(next, Node.class);

        System.out.println(saida.getVizinhos());
        System.out.println(saida.getPosAtual());
        System.out.println(saida.isFim());
        System.out.println(saida.isInicio());

        grafo.insereGrafo(saida.getPosAtual(), saida.getVizinhos());
        System.out.println(grafo.getVizinhosNo(saida.getPosAtual()));
    }
}