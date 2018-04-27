public class Main {

    private static final int B = ConsumerThread.B;
    private static final int C = ConsumerThread.C;
    private static final int D = ConsumerThread.D;

    private static final int m = 4;
    private static final int n = 100*m;
    private static final int k = 2;

    public static void main(String[] args) throws InterruptedException{
        SharedMemory sharedMemory = new SharedMemory(m, n);
        Thread threadA = new ProducerThread(sharedMemory, n);
        Thread threadB = new ConsumerThread(B, sharedMemory);
        Thread threadC = new ConsumerThread(C, sharedMemory);
        Thread threadD = new ConsumerThread(D, sharedMemory);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();
    }
}
