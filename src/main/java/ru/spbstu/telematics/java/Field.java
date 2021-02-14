package ru.spbstu.telematics.java;

import java.util.Random;

public class Field {
    int berries;
    int bound;
    int maxBerries;
    int producedBerries = 0;
    final Random random = new Random();

    public Field(int bound, int berries, int maxBerries) {
        if (bound <= 1 || berries <= 0 || maxBerries <= 0)
            throw new IllegalArgumentException();

        this.berries = berries;
        this.bound = bound;
        this.maxBerries = maxBerries;
    }

    synchronized void addSomeBerries(int growing) {
        while (berries >= maxBerries) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producedBerries += Math.min(growing, maxBerries-berries);
        this.berries += Math.min(growing, maxBerries-berries);
        notifyAll();
    }

    synchronized int getSomeBerries(int maxBerriesCanTake) {
        while (berries < 1) {
            System.out.println("Field is empry: " + berries);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Field before: " + berries);
        int taken = Math.min(berries, random.nextInt(bound));
        taken = Math.min(taken, maxBerriesCanTake);
        berries -= taken;
        System.out.println("Field after:  " + berries);
        notifyAll();
        return taken;
    }

    boolean isEmpty() {
        return berries == 0;
    }
}
