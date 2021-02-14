package ru.spbstu.telematics.java;
import java.util.concurrent.locks.*;

public class Flag {
    Lock lock = new ReentrantLock();

    boolean tryRaiseFlag() {
        return lock.tryLock();
    }

    void lowerFlag() {
        lock.unlock();
    }
}
