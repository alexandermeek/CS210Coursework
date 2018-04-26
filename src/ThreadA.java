import java.util.Random;

public class ThreadA extends Thread {

    private static final int RANGE = 100;

    private SharedMemory sharedMemory;
    private int n;

    public ThreadA(SharedMemory mem, int n) {
        this.sharedMemory = mem;
        this.n = n;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            try {
                sharedMemory.addData(newRandomInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int newRandomInt() {
        Random rand = new Random();
        return rand.nextInt(RANGE);
    }
}
