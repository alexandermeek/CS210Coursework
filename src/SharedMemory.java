import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared memory for producer and consumer threads.
 */
public class SharedMemory {
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int m,countLeft;
    private LinkedList<Integer> numbers = new LinkedList<>();

    /**
     * Creates a new shared memory.
     * @param m The size of the memory.
     * @param countLeft The number of numbers left to pass through the memory.
     */
    public SharedMemory(int m, int countLeft) {
        this.m = m;
        this.countLeft = countLeft;
    }

    /**
     * Gets the counter of numbers left to pass through the memory.
     * @return The counter of numbers.
     */
    public int getCountLeft() {
        lock.lock(); //Locks memory.
        return countLeft;
    }

    /**
     * Peeks at the data at the start of the memory.
     * @return The number/data.
     * @throws InterruptedException
     */
    public int readData() throws InterruptedException{
        while (numbers.size() == 0) { //Check if memory is empty.
            notEmpty.await(); //If memory is empty wait until it is not empty.
        }
        return numbers.peekFirst();
    }

    /**
     * Finish with the memory.
     */
    public void finish() {
        lock.unlock(); //Unlock memory.
    }

    /**
     * Takes the data from the start of the memory.
     * @return The number/data.
     */
    public int takeData() {
        try {
            int temp = numbers.getFirst(); //Gets the first number in memory.
            numbers.removeFirst(); //Removes the data from memory.
            countLeft--; //Reduces the counter of numbers left.
            notFull.signalAll(); //Signal all threads that the memory is not full.
            return temp;
        } finally {
            lock.unlock(); //Unlock memory.
        }
    }

    /**
     * Adds data to the end of the memory.
     * @param data The data to add.
     * @throws InterruptedException
     */
    public void addData(int data) throws InterruptedException{
        lock.lock(); //Locks memory
        try {
            while (numbers.size() == m) { //Check if memory is full.
                notFull.await(); //If memory is full wait until it is not full.
            }
            numbers.addLast(data); //Add number to memory.
            notEmpty.signalAll(); //Signal all threads that the memory is not empty.
        } finally {
            lock.unlock(); //Unlock memory.
        }
    }

    /**
     * Prints out numbers in memory. Debug use.
     * @return
     */
    public String toString () {
        return numbers.toString();
    }
}
