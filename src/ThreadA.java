import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadA extends Thread {
    private int m,n;
    public ThreadA(SharedMemory mem, int m, int n) {
        this.m = m;
        this.n = n;
    }

    public void run() {

    }

    private int newRandomInt() {
        Random rand = new Random();
        return rand.nextInt(n);
    }
}
