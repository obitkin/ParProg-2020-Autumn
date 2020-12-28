package ru.spbstu.telematics.java;
import java.util.Random;

public class Neighbor implements Runnable {

    private Boolean flag;
    private Neighbor neighbor = null;
    private int berries = 0;
    private int maxBerries;
    private Field field;
    private int timeOut;
    private final Random random = new Random();

    public Neighbor(Field field, int timeOut, Boolean flag, int maxBerries) {
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

            synchronized (flag) {
                while (berries > neighbor.progress()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " Is waiting: " + berries);
                        flag.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (field) {
                    berries += field.getSomeBerries();
                    if (berries >= maxBerries) {
                        int extraBerries = berries - maxBerries;
                        if (extraBerries > 0) {
                            field.returnSomeBerries(extraBerries);
                            berries -= extraBerries;
                        }
                    }
                }
                flag.notify();
            }

            System.out.println(Thread.currentThread().getName() + " After:  " + berries);

            if (berries == maxBerries) {
                System.out.println(Thread.currentThread().getName() + " is terminated: " + berries);
                break;
            }

            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


