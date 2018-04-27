import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final int B = ConsumerThread.B;
    private static final int C = ConsumerThread.C;
    private static final int D = ConsumerThread.D;

    private static final int m = 4;
    private static final int n = 100*m;
    private static final int k = 3; //Can only be 2 or 3 at the moment.

    public static void main(String[] args) throws InterruptedException{
        SharedMemory sharedMemory = new SharedMemory(m, n);
        Thread threadA = new ProducerThread(sharedMemory, n);
        Thread threadB = new ConsumerThread(B, sharedMemory);
        Thread threadC = new ConsumerThread(C, sharedMemory);
        Thread threadD;
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

        System.out.println("Finished!, " + totalNumbersWrittenToFile + " numbers total were written to the files.");
    }

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
