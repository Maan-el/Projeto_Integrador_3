import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DFS dfs = new DFS();

        ChamoAPI api = new ChamoAPI();
        ArrayList<Integer> movimentos = null;

        try {
            System.out.println(api.getNomes());
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