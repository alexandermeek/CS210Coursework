import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMemory {
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int m,countLeft;
    private LinkedList<Integer> numbers = new LinkedList<>();

    public SharedMemory(int m, int countLeft) {
        this.m = m;
        this.countLeft = countLeft;
    }

    public int getCountLeft() {
        lock.lock();
        return countLeft;
    }

    public int readData() throws InterruptedException{
        while (numbers.size() == 0) {
            notEmpty.await();
        }
        return numbers.peekFirst();
    }

    public void finish() {
        lock.unlock();
    }

    public int takeData() {
        try {
            int temp = numbers.getFirst();
            numbers.removeFirst();
            countLeft--;
            notFull.signalAll();
            return temp;
        } finally {
            lock.unlock();
        }
    }

    public void addData(int data) throws InterruptedException{
        lock.lock();
        try {
            while (numbers.size() == m) {
                notFull.await();
            }
            numbers.addLast(data);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String toString () {
        return numbers.toString();
    }
}
