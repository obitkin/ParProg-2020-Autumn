

public class AppTest {
    public static void main(String[] args) {
        System.out.println("main");

        Field F = new Field();

        Neighbor N1 = new Neighbor(0,false,F);
        Neighbor N2 = new Neighbor(0,true,F);
        N1.setNeighbor(N2);
        N2.setNeighbor(N1);

        new Thread(N1).start();
        new Thread(N2).start();

    }
}
