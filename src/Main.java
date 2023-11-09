// Todas as classes que estão sendo usadas somente para pegar valores de uma API foram substituidas por Records,
// pois elas possuiam apenas getters e todos os seus valores eram constantes,
// por isso Record declara intenção melhor que Class.

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DFS dfs = new DFS();

        ChamoAPI api = new ChamoAPI();
        ArrayList<Integer> movimentos = null;

        //HACK ambos métodos lançam as mesmas exceções e mostram as mesmas mensagens de erro.
        try {
//            System.out.println(api.getNomes());
            movimentos = dfs.inicio();
        } catch (IOException e) {
            System.err.println("Erro de conexão com a internet, verifique sua conexão");
            System.exit(e.hashCode());
        } catch (InterruptedException e) {
            System.err.println("Processo interrompido");
            System.exit(e.hashCode());
        }

        System.out.println(movimentos);

        System.out.println(movimentos);
    }
}