import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The main class for The CS210 Concurrency Coursework. Implementing one producer and
 * multiple consumers via a shared memory.
 */
public class Main {

    private static final int B = ConsumerThread.B;
    private static final int C = ConsumerThread.C;
    private static final int D = ConsumerThread.D;

    private static int m = 4; //The size of the shared memory.
    private static int n = 100*m; //The number of numbers to be produced.
    private static int k = 2; //Can only be 2 or 3 at the moment.

    public static void main(String[] args) throws InterruptedException{
        if (args.length == 3) {
            k = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);
        }

        SharedMemory sharedMemory = new SharedMemory(m, n);
        Thread threadA = new ProducerThread(sharedMemory, n);
        Thread threadB = new ConsumerThread(B, sharedMemory);
        Thread threadC = new ConsumerThread(C, sharedMemory);
        Thread threadD = new Thread();
        if (k == 3) {
            threadD = new ConsumerThread(D, sharedMemory);
        }

        threadA.start();
        threadB.start();
        threadC.start();
        if (k == 3) {
            threadD.start();
        }

        threadA.join();
        threadB.join();
        threadC.join();
        if (k == 3) {
            threadD.join();
        }

        int totalNumbersWrittenToFile = 0;
        totalNumbersWrittenToFile += countLines("B.txt");
        totalNumbersWrittenToFile += countLines("C.txt");
        if (k == 3) {
            totalNumbersWrittenToFile += countLines("D.txt");
        }

        System.out.println("Finished!, " + totalNumbersWrittenToFile + " numbers total were written to " + k + " files (mem size: " + m + ")");
    }

    /**
     * Counts the number of lines in a give file.
     * @param filename The filepath of the file.
     * @return The number of lines in the file.
     */
    private static int countLines(String filename) {
        int lines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return lines;
        }
    }
}
