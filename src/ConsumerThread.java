/**
 * A consumer thread. It consumes a series of numbers of a particular type
 * from shared memory.
 */
public class ConsumerThread extends Thread {

    public static final int B = 0; //Consumer type B - take even numbers
    public static final int C = 1; //Consumer type C - take odd numbers
    public static final int D = 2; //Consumer type D - take multiples of three

    private static final String B_FILENAME = "B.txt";
    private static final String C_FILENAME = "C.txt";
    private static final String D_FILENAME = "D.txt";

    private int consumerType;
    private SharedMemory sharedMemory;
    private WriteToFile writeToFile;

    /**
     * Creates a new consumer thread. Sets the correct consumer type.
     * @param consumerType The type of consumer thread to create.
     * @param sharedMemory The shared memory.
     */
    public ConsumerThread (int consumerType, SharedMemory sharedMemory) {
        this.sharedMemory = sharedMemory;
        this.consumerType = consumerType;
        switch (this.consumerType) {
            case B:
                this.writeToFile = new WriteToFile(B_FILENAME);
                break;
            case C:
                this.writeToFile = new WriteToFile(C_FILENAME);
                break;
            case D:
                this.writeToFile = new WriteToFile(D_FILENAME);
                break;
            default:
                this.writeToFile = new WriteToFile(B_FILENAME);
                break;
        }

    }

    /**
     * Runs when the thread is started. Consumes numbers from the shared memory.
     */
    public void run() {
        try {
            switch (this.consumerType) {
                case B:
                    bRun();
                    break;
                case C:
                    cRun();
                    break;
                case D:
                    dRun();
                    break;
                default:
                    bRun();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * For a B consumer, take any even numbers from the shared memory.
     * @throws InterruptedException
     */
    private void bRun() throws InterruptedException {
        while (sharedMemory.getCountLeft() != 0) { //Checks if there is anything left to take.
            if (sharedMemory.readData() % 2 == 0) { //Checks if the next number is even.
                writeToFile.writeLine(sharedMemory.takeData()); //Writes the number to a file.
            } else {
                sharedMemory.finish(); //Do nothing with this number.
            }
        }
        sharedMemory.finish(); //Finished with the memory.
        writeToFile.close(); //Close the file.
    }

    /**
     * For a C consumer, take any odd numbers from the shared memory.
     * @throws InterruptedException
     */
    private void cRun() throws InterruptedException {
        while (sharedMemory.getCountLeft() != 0) { //Checks if there is anything left to take.
            if (sharedMemory.readData() % 2 != 0) { //Checks if the next number is odd.
                writeToFile.writeLine(sharedMemory.takeData()); //Writes the number to a file.
            } else {
                sharedMemory.finish(); //Do nothing with this number.
            }
        }
        sharedMemory.finish(); //Finished with the memory.
        writeToFile.close(); //Close the file.
    }

    /**
     * For a D consumer, take any multiples of three from the shared memory.
     * @throws InterruptedException
     */
    private void dRun() throws InterruptedException {
        while (sharedMemory.getCountLeft() != 0) { //Checks if there is anything left to take.
            if (sharedMemory.readData() % 3 == 0) { //Checks if the next number is divisible by 3.
                writeToFile.writeLine(sharedMemory.takeData()); //Writes the number to a file.
            } else {
                sharedMemory.finish(); //Do nothing with this number.
            }
        }
        sharedMemory.finish(); //Finished with the memory.
        writeToFile.close(); //CLose the file.
    }
}

