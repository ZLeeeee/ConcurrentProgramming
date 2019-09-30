package barrier;

public class Test {
    public static void main(String[] args) {
        CellularAutomata cellularAutomata = new CellularAutomata();
        cellularAutomata.start();
        System.out.println(cellularAutomata.getResult());
    }
}
