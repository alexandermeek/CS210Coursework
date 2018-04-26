import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMemory {
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int m,size;
    private LinkedList<Integer> numbers = new LinkedList<>();

    public SharedMemory(int m) {
        this.m = m;
        this.size = 0;
    }

    public int readData() {
        lock.lock();
        return numbers.peekFirst();
    }

    public void doNothing() {
        lock.unlock();
    }

    public int takeData() throws InterruptedException{
        try {
            while(size==0) {
                notEmpty.await();
            }
            int temp = numbers.getFirst();
            numbers.removeFirst();
            this.size--;
            notFull.signalAll();
            return temp;
        } finally {
            lock.unlock();
        }
    }

    public void addData(int data) throws InterruptedException{
        lock.lock();
        try {
            while (size == m) {
                notFull.await();
            }
            numbers.addLast(data);
            this.size++;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String toString () {
        return numbers.toString();
    }
}
