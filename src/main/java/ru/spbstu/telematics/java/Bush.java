package ru.spbstu.telematics.java;

import java.util.Random;

public class Bush implements Runnable  {

    Field field;
    int bound;
    int timeOut;
    private final Random random = new Random();

    Bush(Field field, int bound, int timeOut) {
        if (bound <= 0 || timeOut <= 0)
            throw new IllegalArgumentException();

        this.field = field;
        this.bound = bound;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        if (field == null)
            throw new NullPointerException();

        while (!Thread.currentThread().isInterrupted()) {
            int r = random.nextInt(bound);
            System.out.println("Add: " + r);
            field.addSomeBerries(r);

            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
