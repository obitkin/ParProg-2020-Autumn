import java.util.Random;

public class Field {
    int berries;
    final Random random = new Random();

    public Field(int berries) {
        this.berries = berries;
    }

    int getSomeBerries() {
        if (berries > 0) {
            System.out.println("Field before: " + berries);
            int r = Math.min(berries, random.nextInt(5));
            berries -= r;
            System.out.println("Field after:  " + berries);
            return r;
        }
        else {
            System.out.println("Field is empry: " + berries);
            return 0;
        }
    }

    boolean isEmpty() {
        return berries == 0;
    }
}
