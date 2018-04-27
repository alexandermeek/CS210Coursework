import java.util.Random;

/**
 * A producer thread. It produces a series of random numbers to be placed in
 * a shared memory.
 */
public class ProducerThread extends Thread {

    private static final int RANGE = 100; //The range of the random numbers generated.

    private SharedMemory sharedMemory; //The shared memory to store the numbers in.
    private int n; //The number of numbers to be generated.

    /**
     * Creates a new producer thread.
     * @param sharedMemory The shared memory.
     * @param n The number of numbers to generate.
     */
    public ProducerThread(SharedMemory sharedMemory, int n) {
        this.sharedMemory = sharedMemory;
        this.n = n;
    }

    /**
     * Runs when the thread is started. Generates n, random numbers.
     */
    public void run() {
        for (int i = 0; i < n; i++) {
            try {
                sharedMemory.addData(newRandomInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates a random number.
     * @return A random number.
     */
    private int newRandomInt() {
        Random rand = new Random();
        return rand.nextInt(RANGE);
    }
}
