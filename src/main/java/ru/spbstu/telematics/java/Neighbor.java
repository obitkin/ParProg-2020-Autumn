package ru.spbstu.telematics.java;
import java.util.Random;

public class Neighbor implements Runnable {

    private Flag flag;
    private Neighbor neighbor = null;
    private int berries = 0;
    private int maxBerries;
    private Field field;
    private int timeOut;
    private final Random random = new Random();

    public Neighbor(Field field, Flag flag, int timeOut, int maxBerries) {
        if (timeOut <= 0 || maxBerries <= 0)
            throw new IllegalArgumentException();

        this.field = field;
        this.timeOut = timeOut;
        this.flag = flag;
        this.maxBerries = maxBerries;
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

            if(flag.tryRaiseFlag()) {
                if (this.progress() > neighbor.progress()) {
                    System.out.println(Thread.currentThread().getName() + " Is waiting for progress: " + berries);
                }
                else {
                    berries += field.getSomeBerries(this.maxBerries - this.berries);
                }
                flag.lowerFlag();
            }
            else {
                System.out.println(Thread.currentThread().getName() + " Is waiting for flag: " + berries);
            }

            System.out.println(Thread.currentThread().getName() + " After:  " + berries);

            if (berries == maxBerries) {
                System.out.println(Thread.currentThread().getName() + " is terminated: " + berries);
                break;
            }

            try {
                Thread.sleep(random.nextInt(timeOut)); //do another job 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


