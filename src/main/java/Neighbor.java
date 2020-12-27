
public class Neighbor implements Runnable {

    Neighbor neighbor = null;
    int berries;
    boolean flag;
    Field field;

    public Neighbor(int berries, boolean flag, Field field) {
        this.berries = berries;
        this.flag = flag;
        this.field = field;
    }

    void setNeighbor(Neighbor neighbor) {
        this.neighbor = neighbor;
    }

    int progress() {
        return berries;
    }

    public void run() {
        if (neighbor == null)
            throw new NullPointerException();

        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " Before: " + berries);

            synchronized (field) {
                while (berries > neighbor.progress() && !field.isEmpty()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " Is waiting: " + berries);
                        field.wait(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                field.notify();
                berries += field.getSomeBerries();
            }
            if (field.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " is terminated: " + berries);
                break;
            }
            System.out.println(Thread.currentThread().getName() + " After:  " + berries);
        }
    }
}


