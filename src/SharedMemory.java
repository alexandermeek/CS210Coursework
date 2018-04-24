import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMemory {
    private ReentrantLock lock = new ReentrantLock();
    private int m,size;
    private LinkedList<Integer> numbers = new LinkedList<Integer>();

    public SharedMemory(ReentrantLock lock, int m) {
        this.m = m;
        this.size = 0;
        this.lock = lock;
    }

    public int getSize() {
        return numbers.size();
    }

    public int readData() {
        return numbers.peekFirst();
    }

    public int takeData() {
        if (this.size > 0) {
            int temp = numbers.getFirst();
            numbers.removeFirst();
            this.size--;
            return temp;
        } else {
            return null;
        }
    }

    public void addData(int data) {
        if (this.size < this.m) {
            numbers.addLast(data);
            this.size++;
        }
    }
}
