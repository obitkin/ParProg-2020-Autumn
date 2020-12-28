package ru.spbstu.telematics.java;

import java.util.Random;

public class Field {
    int berries;
    int bound;
    int maxBerries;
    int producedBerries = 0;
    final Random random = new Random();

    public Field(int bound, int berries, int maxBerries) {
        if (bound <= 0 || berries <= 0 || maxBerries <= 0)
            throw new IllegalArgumentException();

        this.berries = berries;
        this.bound = bound;
        this.maxBerries = maxBerries;
    }

    synchronized void returnSomeBerries(int growing) {
        while (berries >= maxBerries) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.berries += Math.min(growing, maxBerries-berries);
        notifyAll();
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

    int getSomeBerries() {
        while (berries < 1) {
            System.out.println("Field is empry: " + berries);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Field before: " + berries);
        int r = Math.min(berries, random.nextInt(bound));
        berries -= r;
        System.out.println("Field after:  " + berries);
        notifyAll();
        return r;

    }

    boolean isEmpty() {
        return berries == 0;
    }
}
