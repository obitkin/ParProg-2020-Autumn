import java.util.Random;

public class Field {
    int berries = 10000;
    final Random random = new Random();

    int getSomeBerries() {
        if (berries > 0) {
            int r = Math.min(berries, random.nextInt(5));
            berries -= r;
            System.out.println("Field: " + berries);
            //notify();
            return r;
        }
        else {
            System.out.println("Field: " + berries);
            //notify();
            return 0;
        }
    }
}
