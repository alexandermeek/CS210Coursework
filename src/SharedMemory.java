import java.util.LinkedList;

public class SharedMemory {
    int m,size;
    LinkedList<Integer> numbers = new LinkedList<Integer>();

    public SharedMemory(int m) {
        this.m = m;
        this.size = 0;
    }

    public int readData() {
        return numbers.peekFirst();
    }

    public int takeData() {
        int temp = numbers.getFirst();
        numbers.removeFirst();
        this.size--;
        return temp;
    }

    public void addData(int data) {
        numbers.addLast(data);
        this.size++;
    }
}
