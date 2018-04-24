import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static final int m = 4;
    private static final int n = 4*m;
    private static final int k = 2;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        SharedMemory sharedMem = new SharedMemory(lock, m);
        Thread threadA = new ThreadA(sharedMem, m, n);
        Thread threadB = new ThreadB();
        Thread threadC = new ThreadC();
    }
}
