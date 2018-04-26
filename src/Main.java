public class Main {

    private static final int m = 4;
    private static final int n = 100*m;
    private static final int k = 2;

    public static void main(String[] args) throws InterruptedException{
        SharedMemory sharedMemory = new SharedMemory(m, n);
        Thread threadA = new ThreadA(sharedMemory, n);
        Thread threadB = new ThreadB(sharedMemory);
        Thread threadC = new ThreadC(sharedMemory);

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();
    }
}
