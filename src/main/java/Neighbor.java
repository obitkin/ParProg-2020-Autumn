

public class Neighbor implements Runnable {

    int berries = 0;
    Neighbor neighbor = null;
    boolean flag = false;
    Field field = null;

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
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (field) {
                while (berries > neighbor.progress()) {
                    try {
                        field.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                field.notify();
                berries += field.getSomeBerries();
            }
            System.out.println(Thread.currentThread().getName() + " " + berries);

        }
    }
}


